package com.example.rankinteractiveassessment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerr {

    @ExceptionHandler(NoFundsException.class)
    public ResponseEntity<Object> noFundsException(Exception exception){
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
    }

    @ExceptionHandler(PlayerNotFoundException.class)
    public ResponseEntity<Object> noPlayerFoundException(Exception exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}
