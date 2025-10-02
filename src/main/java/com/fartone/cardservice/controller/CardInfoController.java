package com.fartone.cardservice.controller;

import com.fartone.cardservice.entity.CardInfo;
import com.fartone.cardservice.service.CardInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cards")
public class CardInfoController {

    @Autowired
    private CardInfoService cardInfoService;

    @PostMapping
    public ResponseEntity<CardInfo> createCard(@RequestBody CardInfo card) {
        CardInfo createdCard = cardInfoService.createCard(card);
        return ResponseEntity.ok(createdCard);
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<CardInfo> createCardForUser(@PathVariable Long userId, @RequestBody CardInfo card) {
        CardInfo createdCard = cardInfoService.createCardForUser(userId, card);
        return ResponseEntity.ok(createdCard);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardInfo> getCardById(@PathVariable Long id) {
        Optional<CardInfo> card = cardInfoService.getCardById(id);
        return card.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/by-ids")
    public ResponseEntity<List<CardInfo>> getCardsByIds(@RequestParam List<Long> ids) {
        List<CardInfo> cards = cardInfoService.getCardsByIds(ids);
        return ResponseEntity.ok(cards);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CardInfo>> getCardsByUserId(@PathVariable Long userId) {
        List<CardInfo> cards = cardInfoService.getCardsByUserId(userId);
        return ResponseEntity.ok(cards);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CardInfo> updateCard(@PathVariable Long id, @RequestBody CardInfo cardDetails) {
        Optional<CardInfo> updatedCard = cardInfoService.updateCard(id, cardDetails);
        return updatedCard.map(ResponseEntity::ok)
                         .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCard(@PathVariable Long id) {
        boolean deleted = cardInfoService.deleteCard(id);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<CardInfo>> getAllCards() {
        List<CardInfo> cards = cardInfoService.getAllCards();
        return ResponseEntity.ok(cards);
    }
}