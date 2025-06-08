package cat.itacademy.blackjack.blackjack_webflux.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import cat.itacademy.blackjack.blackjack_webflux.model.Game;
import cat.itacademy.blackjack.blackjack_webflux.model.GameStatus;
import cat.itacademy.blackjack.blackjack_webflux.model.Player;
import cat.itacademy.blackjack.blackjack_webflux.repository.GameRepository;
import cat.itacademy.blackjack.blackjack_webflux.repository.PlayerRepository;
import cat.itacademy.blackjack.blackjack_webflux.service.impl.GameServiceImpl;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @Mock
    private PlayerRepository playerRepo;

    @Mock
    private GameRepository gameRepo;

    @InjectMocks
    private GameServiceImpl gameService;

    @Test
    void createGame_shouldEmitNewGameWithExpectedFields() {
        // 1) Simulamos que PlayerRepository encuentra un jugador "Alice"
        Player mockPlayer = new Player();
        mockPlayer.setId(1L);
        mockPlayer.setName("Alice");
        when(playerRepo.findByName("Alice"))
                .thenReturn(Mono.just(mockPlayer));

        // 2) Simulamos que GameRepository al guardar asigna el ID "game123"
        when(gameRepo.save(any(Game.class)))
                .thenAnswer(inv -> {
                    Game g = inv.getArgument(0);
                    g.setId("game123");
                    return Mono.just(g);
                });

        // 3) Ejecutamos el método que aún no existe
        Mono<Game> gameMono = gameService.createGame("Alice");

        // 4) Verificamos con StepVerifier el comportamiento esperado
        StepVerifier.create(gameMono)
                .expectNextMatches(game
                        -> "game123".equals(game.getId())
                && game.getPlayerId().equals(1L)
                && "Alice".equals(game.getPlayerName())
                && game.getStatus() == GameStatus.IN_PROGRESS
                )
                .verifyComplete();
    }
}
