package com.example.rankinteractiveassessment.service;

import com.example.rankinteractiveassessment.domain.Player;
import com.example.rankinteractiveassessment.dto.PlayerDTO;
import com.example.rankinteractiveassessment.exception.*;
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
        Player player = playerRepository.getByPlayerId(playerDTO.getPlayerId());


        if (playerRepository.existsByPlayerId(playerDTO.getPlayerId())){
            //check for transaction id
            if(playerDTO.getTransactionId() != null){
                if (player.getTransactionId() != null && player.getTransactionId().equals(playerDTO.getTransactionId())){
                    return;
                } else {
                    player.setTransactionId(playerDTO.getTransactionId());
                }
            } else {
                throw new NoTransactionIdException();
            }
            //check for promotion
            if ( playerDTO.getPromotionCode() != null && !playerDTO.getPromotionCode().isEmpty() ){
                if (promotionRepository.existsByCode(playerDTO.getPromotionCode())){
                    if (player.getPromotionCode() != null && player.getPromotionCode().equals(playerDTO.getPromotionCode())){
                        //check if code has been used 5 times or more
                        if (player.getPromotionCodeCount() <= 5){
                            player.setPromotionCodeCount(player.getPromotionCodeCount() + 1);
                        }else {
                            throw new PromotionUsedException(playerDTO.getPromotionCode());
                        }
                    } else {
                        //add code to player if its valid
                        player.setPromotionCode(playerDTO.getPromotionCode());
                        player.setPromotionCodeCount(1);
                    }
                } else {
                    throw new PromotionInvalidException(playerDTO.getPromotionCode());
                }

            } else {
                player.setAmount(getNewAmount(player, playerDTO));
            }
            playerRepository.save(player);

        } else {
            throw new PlayerNotFoundException();
        }
    }

    private BigDecimal getNewAmount(Player player, PlayerDTO playerDTO){
        BigDecimal newAmount = player.getAmount().subtract(playerDTO.getAmount());
        if (newAmount.compareTo(BigDecimal.ZERO) <= 0){
            throw new NoFundsException();
        } else {
            return newAmount;
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
