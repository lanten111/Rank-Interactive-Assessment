package com.example.rankinteractiveassessment.domain;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotEmpty
    private String playerId;

    @Column(nullable = false, unique = true)
    @NotEmpty
    private String username;

    @Column(nullable = false)
    @NotEmpty
    private BigDecimal amount;

    @Column
    private String transactionId;

    @JoinColumn
    @OneToMany
    private List<PlayerPromotion> playerPromotion = new ArrayList<>();

    @JoinColumn
    @OneToMany
    private List<PlayerTransaction> playerTransactions = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public List<PlayerPromotion> getPlayerPromotion() {
        return playerPromotion;
    }

    public void setPlayerPromotion(List<PlayerPromotion> playerPromotion) {
        this.playerPromotion = playerPromotion;
    }

    public List<PlayerTransaction> getPlayerTransactions() {
        return playerTransactions;
    }

    public void setPlayerTransactions(List<PlayerTransaction> playerTransactions) {
        this.playerTransactions = playerTransactions;
    }
}
