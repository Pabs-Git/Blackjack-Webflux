package cat.itacademy.blackjack.blackjack_webflux.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cat.itacademy.blackjack.blackjack_webflux.dto.NewGameRequest;
import cat.itacademy.blackjack.blackjack_webflux.dto.PlayRequest;
import cat.itacademy.blackjack.blackjack_webflux.dto.UpdatePlayerRequest;
import cat.itacademy.blackjack.blackjack_webflux.model.Game;
import cat.itacademy.blackjack.blackjack_webflux.model.Player;
import cat.itacademy.blackjack.blackjack_webflux.model.RankingEntry;
import cat.itacademy.blackjack.blackjack_webflux.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/game")
public class GameController {

    private final GameService service;

    public GameController(GameService service) {
        this.service = service;
    }

    @Operation(summary = "Create a new game", description = "Creates a new Blackjack game for a given player")
    @ApiResponse(responseCode = "201", description = "Game successfully created")
    @ApiResponse(responseCode = "404", description = "Player not found")
    @PostMapping("/new")
    public Mono<ResponseEntity<Game>> createGame(@RequestBody NewGameRequest req) {
        return service.createGame(req.getPlayerName())
                .map(game -> ResponseEntity
                .status(HttpStatus.CREATED)
                .body(game));
    }

    @Operation(summary = "Get game details", description = "Retrieves details of a specific game by ID")
    @ApiResponse(responseCode = "200", description = "Game found")
    @ApiResponse(responseCode = "404", description = "Game not found")
    @GetMapping("/{id}")
    public Mono<ResponseEntity<Game>> getGame(@PathVariable String id) {
        return service.getGameById(id)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Play a round", description = "Performs a play in an existing game")
    @ApiResponse(responseCode = "200", description = "Play executed")
    @ApiResponse(responseCode = "404", description = "Game not found")
    @PostMapping("/{id}/play")
    public Mono<ResponseEntity<Game>> playRound(@PathVariable String id,
            @RequestBody PlayRequest req) {
        return service.playRound(id, req)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Delete a game", description = "Deletes an existing game by ID")
    @ApiResponse(responseCode = "204", description = "Game deleted successfully")
    @DeleteMapping("/{id}/delete")
    public Mono<ResponseEntity<Void>> deleteGame(@PathVariable String id) {
        return service.deleteGame(id)
                .thenReturn(ResponseEntity.noContent().build());
    }

    @Operation(summary = "Get player ranking", description = "Retrieves ranking of players based on performance")
    @ApiResponse(responseCode = "200", description = "Ranking retrieved")
    @GetMapping("/ranking")
    public Flux<RankingEntry> getRanking() {
        return service.getRanking();
    }

    @Operation(summary = "Update player name", description = "Updates the name of a player in an existing game")
    @ApiResponse(responseCode = "200", description = "Player updated successfully")
    @ApiResponse(responseCode = "404", description = "Player not found")
    @PutMapping("/player/{playerId}")
    public Mono<ResponseEntity<Player>> updatePlayer(@PathVariable Long playerId,
            @RequestBody UpdatePlayerRequest req) {
        return service.updatePlayer(playerId, req.getName())
                .map(ResponseEntity::ok);
    }
}
