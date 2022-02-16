package com.example.rankinteractiveassessment;

import com.example.rankinteractiveassessment.domain.Player;
import com.example.rankinteractiveassessment.domain.PromotionCodes;
import com.example.rankinteractiveassessment.repository.PlayerRepository;
import com.example.rankinteractiveassessment.repository.PromotionCodesRepository;
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
    PromotionCodesRepository promotionCodesRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {Player player = new Player();
        player.setAmount(BigDecimal.valueOf(50));
        player.setUsername("batman");
        player.setPlayerId("12323");
        playerRepository.save(player);

        PromotionCodes promotionCodes = new PromotionCodes();
        promotionCodes.setCode("paper");
        PromotionCodes promotionCodes1 = new PromotionCodes();
        promotionCodes1.setCode("paper2");
        promotionCodesRepository.save(promotionCodes);
        promotionCodesRepository.save(promotionCodes1);

    }
}
