package com.example.rankinteractiveassessment.service;

import com.example.rankinteractiveassessment.domain.Player;
import com.example.rankinteractiveassessment.domain.PlayerPromotion;
import com.example.rankinteractiveassessment.domain.PlayerTransaction;
import com.example.rankinteractiveassessment.domain.PromotionCodes;
import com.example.rankinteractiveassessment.dto.PlayerDTO;
import com.example.rankinteractiveassessment.exception.*;
import com.example.rankinteractiveassessment.repository.PlayerPromotionRepository;
import com.example.rankinteractiveassessment.repository.PlayerRepository;
import com.example.rankinteractiveassessment.repository.PlayerTransactionRepository;
import com.example.rankinteractiveassessment.repository.PromotionCodesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class PlayerService {

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    PromotionCodesRepository promotionCodesRepository;

    @Autowired
    PlayerPromotionRepository playerPromotionRepository;

    @Autowired
    PlayerTransactionRepository playerTransactionRepository;

    public BigDecimal getBalance(PlayerDTO playerDTO){
        return playerRepository.getByPlayerId(playerDTO.getPlayerId()).getAmount();
    }

    @Transactional
    public void wager(PlayerDTO playerDTO){
        Player player = playerRepository.getByPlayerId(playerDTO.getPlayerId());


        if (playerRepository.existsByPlayerId(playerDTO.getPlayerId())){
            //check for transaction id
            if(playerDTO.getTransactionId() != null){
                if (isTransactionUsed(playerDTO)){
                    return;
                }else {
                    PlayerTransaction playerTransaction = new PlayerTransaction();
                    playerTransaction.setTransactionalId(playerDTO.getTransactionId());
                    playerTransactionRepository.save(playerTransaction);
                    player.getPlayerTransactions().add(playerTransaction);
                }
            } else {
                throw new NoTransactionIdException();
            }
            //check for promotion
            PlayerPromotion promotion = getValidPromotion(player);
            if (promotion != null){
                promotion.setPromotionCodeCount((promotion.getPromotionCodeCount() + 1));
                playerPromotionRepository.save(promotion);

            } else if (!isPromotionEmpty(playerDTO)){
                if (!isPromotionValid(playerDTO)){
                    throw new PromotionInvalidException(playerDTO.getPromotionCode());
                } else if (isPromotionUSed(player)){
                    throw new PromotionUsedException(playerDTO.getPromotionCode());
                } else {
                    PlayerPromotion playerPromotion = new PlayerPromotion();
                    PromotionCodes promotionCodes = promotionCodesRepository.getPromotionCodesByCode(playerDTO.getPromotionCode());
                    playerPromotion.setPromotionCodes(promotionCodes);
                    playerPromotion.setPromotionCodeCount(1);
                    playerPromotionRepository.save(playerPromotion);
                    player.getPlayerPromotion().add(playerPromotion);
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

    private boolean hasPromotion(Player player){
        for (PlayerPromotion promotion: player.getPlayerPromotion()){
            return promotion.getPromotionCodeCount() <= 5;
        }
        return false;
    }

    private boolean isPromotionValid(PlayerDTO playerDTO){
        return promotionCodesRepository.existsByCode(playerDTO.getPromotionCode());
    }

    private boolean isPromotionEmpty(PlayerDTO playerDTO){
        return !(playerDTO.getPromotionCode() != null && !playerDTO.getPromotionCode().isEmpty());
    }

    private boolean isPromotionUSed(Player player){
        for (PlayerPromotion promotion: player.getPlayerPromotion()){
            return promotion.getPromotionCodeCount()  > 5;
        }
        return false;
    }

    private boolean isTransactionUsed(PlayerDTO playerDTO){
        return  playerTransactionRepository.existsByTransactionalId(playerDTO.getTransactionId());
    }

    private PlayerPromotion getValidPromotion(Player player){
        for (PlayerPromotion playerPromotion: player.getPlayerPromotion()){
            if (playerPromotion.getPromotionCodeCount() <= 5){
                return playerPromotion;
            };
        }
        return null;
    }

    @Transactional
    public void deposit(PlayerDTO playerDTO){
        Player player = playerRepository.getByPlayerId(playerDTO.getPlayerId());
        BigDecimal newAmount = player.getAmount().add(playerDTO.getAmount());
        player.setAmount(newAmount);
        playerRepository.save(player);
    }
}
