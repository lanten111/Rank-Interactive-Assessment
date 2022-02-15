package com.example.rankinteractiveassessment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlerr {

    @ExceptionHandler(NoFundsException.class)
    public ResponseEntity<Object> noFundsException(Exception exception){
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
    }

    @ExceptionHandler(PlayerNotFoundException.class)
    public ResponseEntity<Object> noPlayerFoundException(Exception exception){
        Map<String, String> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("message", "player "+exception.getMessage()+" Not found");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PromotionUsedException.class)
    public ResponseEntity<Object> promotionUsedException(Exception exception){
        Map<String, String> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("message","promotion code "+exception.getMessage()+" already used 5 times");
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @ExceptionHandler(PromotionInvalidException.class)
    public ResponseEntity<Object> promotionInvalidException(Exception exception){
        Map<String, String> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("message", "promotion "+exception.getMessage()+" is invalid");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
