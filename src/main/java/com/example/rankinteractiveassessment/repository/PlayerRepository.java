package com.example.rankinteractiveassessment.repository;

import com.example.rankinteractiveassessment.domain.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    Player getByPlayerId(String playerId);

    boolean existsByPlayerId(String playerId);
}
