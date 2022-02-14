package com.example.rankinteractiveassessment.service;

import com.example.rankinteractiveassessment.domain.Player;
import com.example.rankinteractiveassessment.dto.PlayerDTO;
import com.example.rankinteractiveassessment.exception.NoFundsException;
import com.example.rankinteractiveassessment.exception.PlayerNotFoundException;
import com.example.rankinteractiveassessment.repository.PlayerRepository;
import com.example.rankinteractiveassessment.repository.PromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Service
public class PlayerService {

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    PromotionRepository promotionRepository;

    public BigDecimal getBalance(PlayerDTO playerDTO){
        return playerRepository.getByPlayerId(playerDTO.getPlayerId()).getAmount();
    }

    @Transactional
    public void wager(PlayerDTO playerDTO){
        if (playerRepository.existsByPlayerId(playerDTO.getPlayerId())){
            Player player = playerRepository.getByPlayerId(playerDTO.getPlayerId());
            if ((!playerDTO.getPromotionCode().isEmpty() && promotionRepository.existsByCode(playerDTO.getPromotionCode())) || player.getPromotionCodeCount() <= 5){
                if (!player.getPromotionCode().isEmpty()){
                    player.setPromotionCode(playerDTO.getPromotionCode());
                    player.setPromotionCodeCount(1);
                    playerRepository.save(player);
                } else {
                    player.setPromotionCodeCount(+1);
                    playerRepository.save(player);
                }
            } else {
                BigDecimal newAmount = player.getAmount().subtract(playerDTO.getAmount());
                if (newAmount.compareTo(BigDecimal.ZERO) <= 0){
                    throw new NoFundsException();
                } else {
                    player.setAmount(newAmount);
                    playerRepository.save(player);
                }
            }

        } else {
            throw new PlayerNotFoundException();
        }
    }

    @Transactional
    public void deposit(PlayerDTO playerDTO){
        Player player = playerRepository.getByPlayerId(playerDTO.getPlayerId());
        BigDecimal newAmount = player.getAmount().add(playerDTO.getAmount());
        player.setAmount(newAmount);
        playerRepository.save(player);
    }
}
