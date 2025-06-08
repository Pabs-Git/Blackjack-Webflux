package cat.itacademy.blackjack.blackjack_webflux.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import cat.itacademy.blackjack.blackjack_webflux.model.Player;
import reactor.core.publisher.Mono;

public interface PlayerRepository extends ReactiveCrudRepository<Player, Long> {

    Mono<Player> findByName(String name);
}
