package com.example.rankinteractiveassessment.repository;

import com.example.rankinteractiveassessment.domain.PromotionCodes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionCodesRepository extends JpaRepository<PromotionCodes, Long> {

    boolean existsByCode(String code);

    PromotionCodes getPromotionCodesByCode(String code);
}
