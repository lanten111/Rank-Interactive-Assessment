package com.example.rankinteractiveassessment.service;

import com.example.rankinteractiveassessment.domain.Player;
import com.example.rankinteractiveassessment.dto.PlayerDTO;
import com.example.rankinteractiveassessment.exception.NoFundsException;
import com.example.rankinteractiveassessment.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Service
public class PlayerService {

    @Autowired
    PlayerRepository playerRepository;

    public BigDecimal getBalance(PlayerDTO playerDTO){
        return playerRepository.getByPlayerId(playerDTO.getPlayerId()).getAmount();
    }

    @Transactional
    public void wager(PlayerDTO playerDTO){
        Player player = playerRepository.getByPlayerId(playerDTO.getPlayerId());
        BigDecimal newAmount = player.getAmount().subtract(playerDTO.getAmount());
        if (newAmount.compareTo(BigDecimal.ZERO) <= 0){
            throw new NoFundsException();
        }
        player.setAmount(newAmount);
        playerRepository.save(player);
    }

    @Transactional
    public void deposit(PlayerDTO playerDTO){
        Player player = playerRepository.getByPlayerId(playerDTO.getPlayerId());
        BigDecimal newAmount = player.getAmount().add(playerDTO.getAmount());
        player.setAmount(newAmount);
        playerRepository.save(player);
    }


}
