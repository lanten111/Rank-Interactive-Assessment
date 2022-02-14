package com.example.rankinteractiveassessment.repository;

import com.example.rankinteractiveassessment.domain.Promotion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {

    boolean existsByCode(String code);
}
