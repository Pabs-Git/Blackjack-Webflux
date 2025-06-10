package cat.itacademy.blackjack.blackjack_webflux.controller;

import cat.itacademy.blackjack.blackjack_webflux.dto.NewGameRequest;
import cat.itacademy.blackjack.blackjack_webflux.dto.PlayRequest;
import cat.itacademy.blackjack.blackjack_webflux.dto.UpdatePlayerRequest;
import cat.itacademy.blackjack.blackjack_webflux.model.Game;
import cat.itacademy.blackjack.blackjack_webflux.model.GameStatus;
import cat.itacademy.blackjack.blackjack_webflux.model.Player;
import cat.itacademy.blackjack.blackjack_webflux.model.RankingEntry;
import cat.itacademy.blackjack.blackjack_webflux.service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class GameControllerTest {

    private WebTestClient client;
    @Mock
    private GameService service;

    @BeforeEach
    void setUp() {
        GameController controller = new GameController(service);
        client = WebTestClient.bindToController(controller).build();
    }

    @Test
    void createGame_returns201() {
        Game g = new Game("123", 1L, "Jason", null, 0, GameStatus.IN_PROGRESS);
        given(service.createGame(anyString())).willReturn(Mono.just(g));

        client.post().uri("/game/new")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new NewGameRequest("Jason"))
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    void getGameById_returns200() {
        Game g = new Game("123", 1L, "Jason", null, 0, GameStatus.IN_PROGRESS);
        given(service.getGameById("123")).willReturn(Mono.just(g));

        client.get().uri("/game/{id}", "123")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void playRound_returns200() {
        Game g = new Game("123", 1L, "Jason", null, 0, GameStatus.IN_PROGRESS);
        given(service.playRound(eq("123"), any(PlayRequest.class)))
                .willReturn(Mono.just(g));

        client.post().uri("/game/{id}/play", "123")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new PlayRequest("HIT", 10.0))
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void deleteGame_returns204() {
        given(service.deleteGame("123")).willReturn(Mono.empty());

        client.delete().uri("/game/{id}/delete", "123")
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void getRanking_returns200List() {
        RankingEntry r = new RankingEntry(1L, "Jason", 5, 10);
        given(service.getRanking()).willReturn(Flux.just(r));

        client.get().uri("/ranking")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(RankingEntry.class)
                .hasSize(1);
    }

    @Test
    void updatePlayer_returns200Updated() {
        Player p = new Player(1L, "Jason", 0, 0);
        given(service.updatePlayer(1L, "NewJason")).willReturn(Mono.just(p));

        client.put().uri("/player/{playerId}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UpdatePlayerRequest("NewJason"))
                .exchange()
                .expectStatus().isOk();
    }
}
