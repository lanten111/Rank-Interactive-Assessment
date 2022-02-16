package com.example.rankinteractiveassessment.repository;

import com.example.rankinteractiveassessment.domain.PlayerTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerTransactionRepository extends JpaRepository<PlayerTransaction, Long> {
    boolean existsByTransactionalId(String transactionId);
}