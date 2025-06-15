package cat.itacademy.blackjack.blackjack_webflux.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request to create a new Blackjack game")
public class NewGameRequest {

    @Schema(description = "Name of the player starting the game", example = "Jason")
    private String playerName;
}
