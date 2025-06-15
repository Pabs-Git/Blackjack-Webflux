package cat.itacademy.blackjack.blackjack_webflux.service;

import cat.itacademy.blackjack.blackjack_webflux.dto.PlayRequest;
import cat.itacademy.blackjack.blackjack_webflux.model.Game;
import cat.itacademy.blackjack.blackjack_webflux.model.Player;
import cat.itacademy.blackjack.blackjack_webflux.model.RankingEntry;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GameService {

    Mono<Game> createGame(String playerName);

    Mono<Game> getGameById(String id);

    Mono<Game> playRound(String id, PlayRequest playDto);

    Mono<Void> deleteGame(String id);

    Flux<RankingEntry> getRanking();

    Mono<Player> updatePlayer(Long playerId, String newName);
}
