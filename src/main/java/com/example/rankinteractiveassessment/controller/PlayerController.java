package com.example.rankinteractiveassessment.controller;

import com.example.rankinteractiveassessment.dto.PlayerDTO;
import com.example.rankinteractiveassessment.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/casino/")
public class PlayerController {

    @Autowired
    PlayerService playerService;

    @GetMapping("get-balance")
    public ResponseEntity<BigDecimal> getBalance(PlayerDTO playerDTO){
        return ResponseEntity.ok(playerService.getBalance(playerDTO));
    }
}
