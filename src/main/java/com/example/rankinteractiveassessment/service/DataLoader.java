package com.example.rankinteractiveassessment.service;

import com.example.rankinteractiveassessment.domain.Player;
import com.example.rankinteractiveassessment.domain.Promotion;
import com.example.rankinteractiveassessment.repository.PlayerRepository;
import com.example.rankinteractiveassessment.repository.PromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    PromotionRepository promotionRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {Player player = new Player();
        player.setAmount(BigDecimal.valueOf(50));
        player.setUsername("batman");
        player.setPlayerId("12323");
        playerRepository.save(player);

        Promotion promotion = new Promotion();
        promotion.setCode("paper");
        promotionRepository.save(promotion);

    }
}
