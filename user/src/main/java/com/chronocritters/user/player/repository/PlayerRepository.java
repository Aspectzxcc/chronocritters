package com.chronocritters.user.player.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.chronocritters.lib.model.domain.Player;

@Repository
public interface PlayerRepository extends MongoRepository<Player, String> {
    @Query(value = "{ '_id': ?0, 'matchHistory.battleId': ?1 }", fields = "{ 'matchHistory.$': 1 }")
    Optional<Player> findMatchHistoryEntryByPlayerIdAndBattleId(String playerId, String battleId);
}
