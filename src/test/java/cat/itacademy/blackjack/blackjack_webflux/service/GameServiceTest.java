package cat.itacademy.blackjack.blackjack_webflux.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import cat.itacademy.blackjack.blackjack_webflux.model.Game;
import cat.itacademy.blackjack.blackjack_webflux.model.GameStatus;
import cat.itacademy.blackjack.blackjack_webflux.model.Player;
import cat.itacademy.blackjack.blackjack_webflux.model.RankingEntry;
import cat.itacademy.blackjack.blackjack_webflux.repository.GameRepository;
import cat.itacademy.blackjack.blackjack_webflux.repository.PlayerRepository;
import cat.itacademy.blackjack.blackjack_webflux.service.impl.GameServiceImpl;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @Mock
    private PlayerRepository playerRepo;
    @Mock
    private GameRepository gameRepo;
    @InjectMocks
    private GameServiceImpl service;

    @Test
    void createGame_withExistingPlayer_returnsNewGame() {
        Player p = new Player(1L, "Jason", 0, 0);
        when(playerRepo.findByName("Jason")).thenReturn(Mono.just(p));
        when(gameRepo.save(any(Game.class))).thenAnswer(inv -> Mono.just(inv.getArgument(0)));

        StepVerifier.create(service.createGame("Jason"))
                .assertNext(g -> {
                    assertNotNull(g.getId());
                    assertEquals(GameStatus.IN_PROGRESS, g.getStatus());
                })
                .verifyComplete();
    }

    @Test
    void createGame_whenPlayerNotFound_emitsError() {
        when(playerRepo.findByName(anyString())).thenReturn(Mono.empty());
        StepVerifier.create(service.createGame("Nobody"))
                .expectErrorMatches(ex -> ex instanceof RuntimeException
                && ex.getMessage().equals("Player not found"))
                .verify();
    }

    @Test
    void getGameById_withExistingId_returnsGame() {
        Game g = new Game("gid", 1L, "Jason", null, 0, GameStatus.IN_PROGRESS);
        when(gameRepo.findById("gid")).thenReturn(Mono.just(g));
        StepVerifier.create(service.getGameById("gid"))
                .assertNext(game -> assertEquals("gid", game.getId()))
                .verifyComplete();
    }

    @Test
    void getGameById_notFound_emitsError() {
        when(gameRepo.findById(anyString())).thenReturn(Mono.empty());
        StepVerifier.create(service.getGameById("none"))
                .expectErrorMatches(ex -> ex instanceof RuntimeException
                && ex.getMessage().equals("Game not found"))
                .verify();
    }

    @Test
    void deleteGame_completes() {
        when(gameRepo.deleteById("gid")).thenReturn(Mono.empty());
        StepVerifier.create(service.deleteGame("gid")).verifyComplete();
    }

    @Test
    void getRanking_returnsList() {
        Player p1 = new Player(1L, "Jason", 5, 10);
        Player p2 = new Player(2L, "Alice", 3, 8);

        when(playerRepo.findAllByOrderByTotalWinsDesc())
                .thenReturn(Flux.just(p1, p2));

        RankingEntry r1 = new RankingEntry(1L, "Jason", 5, 10);
        RankingEntry r2 = new RankingEntry(2L, "Alice", 3, 8);

        StepVerifier.create(service.getRanking())
                .expectNext(r1, r2)
                .verifyComplete();
    }

    @Test
    void updatePlayer_withExisting_returnsUpdated() {
        Player p = new Player(1L, "Old", 0, 0);
        when(playerRepo.findById(1L)).thenReturn(Mono.just(p));
        when(playerRepo.save(any(Player.class))).thenAnswer(inv -> Mono.just(inv.getArgument(0)));
        StepVerifier.create(service.updatePlayer(1L, "NewName"))
                .assertNext(u -> assertEquals("NewName", u.getName()))
                .verifyComplete();
    }

    @Test
    void updatePlayer_notFound_emitsError() {
        when(playerRepo.findById(anyLong())).thenReturn(Mono.empty());
        StepVerifier.create(service.updatePlayer(99L, "Name"))
                .expectErrorMatches(ex -> ex instanceof RuntimeException
                && ex.getMessage().equals("Player not found"))
                .verify();
    }
}
