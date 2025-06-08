package cat.itacademy.blackjack.blackjack_webflux.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import cat.itacademy.blackjack.blackjack_webflux.dto.NewGameRequest;
import cat.itacademy.blackjack.blackjack_webflux.model.Game;
import cat.itacademy.blackjack.blackjack_webflux.model.GameStatus;
import cat.itacademy.blackjack.blackjack_webflux.service.GameService;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class GameControllerTest {

    private WebTestClient client;

    @Mock
    private GameService gameService;

    @BeforeEach
    void setUp() {
        GameController controller = new GameController(gameService);
        this.client = WebTestClient.bindToController(controller).build();
    }

    @Test
    void newGameEndpoint_returns201WithJason() {
        // Prepare mock game with id "123" and playerName "Jason"
        Game mockGame = new Game();
        mockGame.setId("123");
        mockGame.setPlayerId(1L);
        mockGame.setPlayerName("Jason");
        mockGame.setStatus(GameStatus.IN_PROGRESS);

        given(gameService.createGame(anyString()))
                .willReturn(Mono.just(mockGame));

        client.post()
                .uri("/game/new")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new NewGameRequest("Jason"))
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isEqualTo("123")
                .jsonPath("$.playerName").isEqualTo("Jason")
                .jsonPath("$.status").isEqualTo("IN_PROGRESS");
    }

    @Test
    void newGameEndpoint_whenServiceError_returns5xx() {
        // Simulate service error
        given(gameService.createGame(anyString()))
                .willReturn(Mono.error(new RuntimeException("Some error")));

        client.post()
                .uri("/game/new")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new NewGameRequest("Jason"))
                .exchange()
                .expectStatus().is5xxServerError();
    }
}
