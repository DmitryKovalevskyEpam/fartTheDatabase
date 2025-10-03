package com.example.serserviceapp.service;

import com.example.serserviceapp.model.entity.CardInfo;
import com.example.serserviceapp.model.entity.User;
import com.example.serserviceapp.repository.CardInfoRepository;
import com.example.serserviceapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CardInfoService {

    @Autowired
    private CardInfoRepository cardInfoRepository;

    @Autowired
    private UserRepository userRepository;

    public CardInfo createCardForUser (Long userId, CardInfo card) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User  with id " + userId + " not found"));

        if (cardInfoRepository.existsByNumber(card.getNumber())) {
            throw new RuntimeException("Card with number " + card.getNumber() + " already exists");
        }

        card.setUser (user);
        return cardInfoRepository.save(card);
    }

    public List<CardInfo> createCardsForUser (Long userId, List<CardInfo> cards) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User  with id " + userId + " not found"));

        for (CardInfo card : cards) {
            card.setUser (user);
        }

        return cardInfoRepository.saveAll(cards);
    }

    @Transactional(readOnly = true)
    public Optional<CardInfo> getCardById(Long id) {
        return cardInfoRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<CardInfo> getCardsByIds(List<Long> ids) {
        return cardInfoRepository.findByIds(ids);
    }

    @Transactional(readOnly = true)
    public List<CardInfo> getCardsByUserId(Long userId) {
        return cardInfoRepository.findByUserId(userId);  // Named method
    }

    @Transactional(readOnly = true)
    public List<CardInfo> getCardsByUserEmail(String email) {
        return cardInfoRepository.findByUserEmail(email);
    }

    @Transactional(readOnly = true)
    public List<CardInfo> getAllCards() {
        return cardInfoRepository.findAll();
    }

    public void updateCard(Long id, String number, String holder, LocalDate expirationDate) {
        if (!cardInfoRepository.existsById(id)) {
            throw new RuntimeException("Card with id " + id + " not found");
        }
        cardInfoRepository.updateCard(id, number, holder, expirationDate);
    }

    public CardInfo updateCard(CardInfo card) {
        if (!cardInfoRepository.existsById(card.getId())) {
            throw new RuntimeException("Card with id " + card.getId() + " not found");
        }
        return cardInfoRepository.save(card);
    }

    public void deleteCard(Long id) {
        cardInfoRepository.deleteById(id);
    }

    public void deleteCardsByUserId(Long userId) {
        cardInfoRepository.deleteByUserId(userId);
    }

    public boolean cardExists(Long id) {
        return cardInfoRepository.existsById(id);
    }
}