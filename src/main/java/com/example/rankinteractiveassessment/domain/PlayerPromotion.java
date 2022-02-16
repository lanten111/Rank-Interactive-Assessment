package com.example.rankinteractiveassessment.domain;

import javax.persistence.*;

@Entity
public class PlayerPromotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private int promotionCodeCount;

    @JoinColumn(referencedColumnName = "id")
    @OneToOne
    private Player player;

    @JoinColumn
    @ManyToOne
    private PromotionCodes promotionCodes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPromotionCodeCount() {
        return promotionCodeCount;
    }

    public void setPromotionCodeCount(int promotionCodeCount) {
        this.promotionCodeCount = promotionCodeCount;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public PromotionCodes getPromotionCodes() {
        return promotionCodes;
    }

    public void setPromotionCodes(PromotionCodes promotionCodes) {
        this.promotionCodes = promotionCodes;
    }
}
