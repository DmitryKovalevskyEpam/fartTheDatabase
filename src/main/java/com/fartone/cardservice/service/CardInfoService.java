package com.fartone.cardservice.service;

import com.fartone.cardservice.entity.CardInfo;
import com.fartone.cardservice.dao.CardInfoRepository;
import com.fartone.cardservice.entity.User;
import com.fartone.cardservice.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = "cards")
public class CardInfoService {

    @Autowired
    private CardInfoRepository cardInfoRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    @CacheEvict(allEntries = true)
    public CardInfo createCard(CardInfo card) {
        return cardInfoRepository.save(card);
    }

    @Transactional
    public CardInfo createCardForUser(Long userId, CardInfo card) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        card.setUser(user);
        return cardInfoRepository.save(card);
    }

    @Cacheable(key = "#id")
    public Optional<CardInfo> getCardById(Long id) {
        return cardInfoRepository.findById(id);
    }

    public List<CardInfo> getCardsByIds(List<Long> ids) {
        return cardInfoRepository.findByIdIn(ids);
    }

    public List<CardInfo> getCardsByUserId(Long userId) {
        return cardInfoRepository.findByUserId(userId);
    }

    @Transactional
    @CachePut(key = "#id")
    @CacheEvict(allEntries = true)
    public Optional<CardInfo> updateCard(Long id, CardInfo cardDetails) {
        return cardInfoRepository.findById(id).map(card -> {
            card.setNumber(cardDetails.getNumber());
            card.setHolder(cardDetails.getHolder());
            card.setExpirationDate(cardDetails.getExpirationDate());
            return cardInfoRepository.save(card);
        });
    }

    @Transactional
    @CacheEvict(key = "#id", allEntries = true)
    public boolean deleteCard(Long id) {
        if (cardInfoRepository.existsById(id)) {
            cardInfoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<CardInfo> getAllCards() {
        return cardInfoRepository.findAll();
    }
}
