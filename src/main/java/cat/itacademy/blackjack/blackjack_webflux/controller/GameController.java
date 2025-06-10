package cat.itacademy.blackjack.blackjack_webflux.controller;

import cat.itacademy.blackjack.blackjack_webflux.dto.NewGameRequest;
import cat.itacademy.blackjack.blackjack_webflux.dto.PlayRequest;
import cat.itacademy.blackjack.blackjack_webflux.dto.UpdatePlayerRequest;
import cat.itacademy.blackjack.blackjack_webflux.model.Game;
import cat.itacademy.blackjack.blackjack_webflux.model.Player;
import cat.itacademy.blackjack.blackjack_webflux.model.RankingEntry;
import cat.itacademy.blackjack.blackjack_webflux.service.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/game")
public class GameController {

    private final GameService service;

    public GameController(GameService service) {
        this.service = service;
    }

    @PostMapping("/new")
    public Mono<ResponseEntity<Game>> createGame(@RequestBody NewGameRequest req) {
        return service.createGame(req.getPlayerName())
                .map(g -> ResponseEntity.status(HttpStatus.CREATED).body(g));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Game>> getGame(@PathVariable String id) {
        return service.getGameById(id)
                .map(g -> ResponseEntity.ok(g));
    }

    @PostMapping("/{id}/play")
    public Mono<ResponseEntity<Game>> playRound(@PathVariable String id,
            @RequestBody PlayRequest req) {
        return service.playRound(id, req)
                .map(g -> ResponseEntity.ok(g));
    }

    @DeleteMapping("/{id}/delete")
    public Mono<ResponseEntity<Void>> deleteGame(@PathVariable String id) {
        return service.deleteGame(id)
                .thenReturn(ResponseEntity.noContent().build());
    }

    @GetMapping("/ranking")
    public Flux<RankingEntry> getRanking() {
        return service.getRanking();
    }

    @PutMapping("/player/{playerId}")
    public Mono<ResponseEntity<Player>> updatePlayer(@PathVariable Long playerId,
            @RequestBody UpdatePlayerRequest req) {
        return service.updatePlayer(playerId, req.getName())
                .map(p -> ResponseEntity.ok(p));
    }
}
