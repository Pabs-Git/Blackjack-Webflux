package cat.itacademy.blackjack.blackjack_webflux.service;

import cat.itacademy.blackjack.blackjack_webflux.model.Game;
import reactor.core.publisher.Mono;

public interface GameService {

    Mono<Game> createGame(String playerName);
}
