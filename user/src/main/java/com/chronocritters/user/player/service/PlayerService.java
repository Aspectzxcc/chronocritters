package com.chronocritters.user.player.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.chronocritters.lib.model.domain.MatchHistoryEntry;
import com.chronocritters.lib.model.domain.Player;
import com.chronocritters.lib.model.domain.User;
import com.chronocritters.user.player.repository.PlayerRepository;
import com.chronocritters.user.player.repository.UserRepository;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@GraphQLApi
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final UserRepository userRepository;

    public List<Player> findAll() {
        return playerRepository.findAll();
    }

    @GraphQLQuery(name = "getPlayer")
    public Player findById(@GraphQLArgument(name = "id") String id) {
        return playerRepository.findById(id).orElse(null);
    }

    public Player save(Player player) {
        return playerRepository.save(player);
    }

    public void deleteById(String id) {
        playerRepository.deleteById(id);
    }

    @GraphQLQuery(name = "username")
    public String getUsername(@GraphQLContext Player player) {
        return userRepository.findById(player.getUserId())
                .map(User::getUsername)
                .orElse("Unknown User");
    }

    @GraphQLQuery(name = "getMatchHistoryEntry")
    public MatchHistoryEntry getMatchHistoryEntry(
        @GraphQLArgument(name = "playerId") String playerId,
        @GraphQLArgument(name = "battleId") String battleId
    ) {
        Optional<Player> player = playerRepository.findMatchHistoryEntryByPlayerIdAndBattleId(playerId, battleId);
        if (player.isPresent() && player.get().getMatchHistory() != null && !player.get().getMatchHistory().isEmpty()) {
            return player.get().getMatchHistory().get(0); // only one entry should match
        }
        return null;
    }
}
