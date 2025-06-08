package cat.itacademy.blackjack.blackjack_webflux.service.impl;

import org.springframework.stereotype.Service;

import cat.itacademy.blackjack.blackjack_webflux.model.Game;
import cat.itacademy.blackjack.blackjack_webflux.model.GameStatus;
import cat.itacademy.blackjack.blackjack_webflux.repository.GameRepository;
import cat.itacademy.blackjack.blackjack_webflux.repository.PlayerRepository;
import cat.itacademy.blackjack.blackjack_webflux.service.GameService;
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
                    Game newGame = new Game();
                    newGame.setPlayerId(player.getId());
                    newGame.setPlayerName(player.getName());
                    newGame.setStatus(GameStatus.IN_PROGRESS);
                    return gameRepo.save(newGame);
                });
    }
}
