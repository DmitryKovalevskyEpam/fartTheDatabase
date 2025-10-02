package com.fartone.cardservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Future;

import java.time.LocalDate;

@Entity
@Table(name = "card_info")
public class CardInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotBlank
    @Column(name = "number", nullable = false, length = 19)
    private String number;

    @NotBlank
    @Column(name = "holder", nullable = false)
    private String holder;

    @NotNull
    @Future
    @Column(name = "expiration_date", nullable = false)
    private LocalDate expirationDate;

    // Constructors
    public CardInfo() {}

    public CardInfo(User user, String number, String holder, LocalDate expirationDate) {
        this.user = user;
        this.number = number;
        this.holder = holder;
        this.expirationDate = expirationDate;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }

    public String getHolder() { return holder; }
    public void setHolder(String holder) { this.holder = holder; }

    public LocalDate getExpirationDate() { return expirationDate; }
    public void setExpirationDate(LocalDate expirationDate) { this.expirationDate = expirationDate; }
}
