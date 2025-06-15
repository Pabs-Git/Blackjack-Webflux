package cat.itacademy.blackjack.blackjack_webflux.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import cat.itacademy.blackjack.blackjack_webflux.model.Game;

public interface GameRepository extends ReactiveMongoRepository<Game, String> {
}
