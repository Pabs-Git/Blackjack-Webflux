package cat.itacademy.blackjack.blackjack_webflux.service.impl;

import cat.itacademy.blackjack.blackjack_webflux.model.Game;
import cat.itacademy.blackjack.blackjack_webflux.model.GameStatus;
import cat.itacademy.blackjack.blackjack_webflux.model.Player;
import cat.itacademy.blackjack.blackjack_webflux.model.RankingEntry;
import cat.itacademy.blackjack.blackjack_webflux.repository.GameRepository;
import cat.itacademy.blackjack.blackjack_webflux.repository.PlayerRepository;
import cat.itacademy.blackjack.blackjack_webflux.service.GameService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class GameServiceImpl implements GameService {

    private final PlayerRepository playerRepo;
    private final GameRepository gameRepo;

    public GameServiceImpl(PlayerRepository playerRepo, GameRepository gameRepo) {
        this.playerRepo = playerRepo;
        this.gameRepo = gameRepo;
    }

    @Override
    public Mono<Game> createGame(String playerName) {
        return playerRepo.findByName(playerName)
                .switchIfEmpty(Mono.error(new RuntimeException("Player not found")))
                .flatMap(player -> {
                    Game newGame = Game.builder()
                            .playerId(player.getId())
                            .playerName(player.getName())
                            .status(GameStatus.IN_PROGRESS)
                            .build();
                    return gameRepo.save(newGame);
                });
    }

    @Override
    public Mono<Game> getGameById(String id) {
        return gameRepo.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Game not found")));
    }

    @Override
    public Mono<Game> playRound(String id, Object playDto) {
        return getGameById(id)
                .flatMap(game -> gameRepo.save(game));
    }

    @Override
    public Mono<Void> deleteGame(String id) {
        return gameRepo.deleteById(id);
    }

    @Override
    public Flux<RankingEntry> getRanking() {
        return gameRepo.getRanking();
    }

    @Override
    public Mono<Player> updatePlayer(Long playerId, String newName) {
        return playerRepo.findById(playerId)
                .switchIfEmpty(Mono.error(new RuntimeException("Player not found")))
                .flatMap(player -> {
                    player.setName(newName);
                    return playerRepo.save(player);
                });
    }
}
