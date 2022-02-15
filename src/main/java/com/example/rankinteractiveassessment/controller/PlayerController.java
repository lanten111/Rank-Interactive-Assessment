package com.example.rankinteractiveassessment.controller;

import com.example.rankinteractiveassessment.dto.PlayerDTO;
import com.example.rankinteractiveassessment.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/casino/")
public class PlayerController {

    @Autowired
    PlayerService playerService;

    @GetMapping("get-balance")
    public ResponseEntity<BigDecimal> getBalance(@RequestBody PlayerDTO playerDTO){
        return ResponseEntity.ok(playerService.getBalance(playerDTO));
    }

    @PostMapping(value = "wagering", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void wagering(@RequestBody PlayerDTO playerDTO){
        playerService.wager(playerDTO);
    }

    @PostMapping("depositing")
    public ResponseEntity<Object> depositing(PlayerDTO playerDTO){
        playerService.deposit(playerDTO);
        return ResponseEntity.ok().build();
    }

}
