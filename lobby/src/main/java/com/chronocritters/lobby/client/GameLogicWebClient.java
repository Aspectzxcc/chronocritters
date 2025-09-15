// In lobby/src/main/java/com/chronocritters/lobby/client/GameLogicWebClient.java
package com.chronocritters.lobby.client;

import com.chronocritters.lib.dto.BattleRequest;
import com.chronocritters.lib.model.battle.BattleState;
import com.chronocritters.lib.client.BaseWebClient;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class GameLogicWebClient extends BaseWebClient {

    public GameLogicWebClient(WebClient.Builder webClientBuilder) {
        super(webClientBuilder, "http://localhost:8082");
    }

    public Mono<BattleState> getBattleState(String battleId) {
        return get("/battle/{battleId}", BattleState.class, battleId);
    }

    public Mono<Void> createBattle(String battleId, String playerOneId, String playerTwoId) {
        BattleRequest battleRequest = new BattleRequest(playerOneId, playerTwoId);
        return post("/battle/{battleId}", battleRequest, Void.class, battleId);
    }

    public Mono<Void> handleTurnTimeout(String battleId) {
        return post("/battle/{battleId}/timeout", null, Void.class, new Object[]{battleId});
    }
}