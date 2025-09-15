package com.chronocritters.gamelogic.client;

import com.chronocritters.lib.model.battle.BattleState;
import com.chronocritters.lib.client.BaseWebClient;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class LobbyWebClient extends BaseWebClient {

    public LobbyWebClient(WebClient.Builder webClientBuilder) {
        super(webClientBuilder, "http://localhost:8081");
    }

    public Mono<Void> updateBattleState(String battleId, BattleState battleState) {
        return post("/battle/{battleId}/update", battleState, Void.class, battleId);
    }
}