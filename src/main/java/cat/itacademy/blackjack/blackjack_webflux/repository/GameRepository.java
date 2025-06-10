package cat.itacademy.blackjack.blackjack_webflux.repository;

import cat.itacademy.blackjack.blackjack_webflux.model.Game;
import cat.itacademy.blackjack.blackjack_webflux.model.RankingEntry;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface GameRepository extends ReactiveMongoRepository<Game, String> {

    Flux<RankingEntry> getRanking();
}
