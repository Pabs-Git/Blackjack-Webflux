package cat.itacademy.blackjack.blackjack_webflux.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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
    void createGame_withExistingPlayer_returnsNewGame() {
        // Mock player with name Jason and id 1L
        Player mockPlayer = new Player(1L, "Jason", 0, 0);
        when(playerRepo.findByName("Jason")).thenReturn(Mono.just(mockPlayer));

        // Mock game save to return id "123"
        when(gameRepo.save(any(Game.class))).thenAnswer(inv -> {
            Game g = inv.getArgument(0);
            g.setId("123");
            return Mono.just(g);
        });

        StepVerifier.create(gameService.createGame("Jason"))
                .assertNext(game -> {
                    assertEquals("123", game.getId());
                    assertEquals(1L, game.getPlayerId());
                    assertEquals("Jason", game.getPlayerName());
                    assertEquals(GameStatus.IN_PROGRESS, game.getStatus());
                })
                .verifyComplete();
    }

    @Test
    void createGame_whenPlayerNotFound_emitsError() {
        // Simulate no player found
        when(playerRepo.findByName(anyString())).thenReturn(Mono.empty());

        StepVerifier.create(gameService.createGame("Unknown"))
                .expectErrorSatisfies(throwable -> {
                    assertTrue(throwable instanceof RuntimeException);
                    assertEquals("Player not found", throwable.getMessage());
                })
                .verify();
    }
}
