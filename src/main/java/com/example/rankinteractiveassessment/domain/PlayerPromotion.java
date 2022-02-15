package com.example.rankinteractiveassessment.domain;

import javax.persistence.*;

@Entity
public class PlayerPromotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column()
    @ManyToOne
    private Player user;

    @Column()
    @ManyToOne
    private Promotion promotion;
}
