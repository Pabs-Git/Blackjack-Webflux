package cat.itacademy.blackjack.blackjack_webflux.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request to update a player's name")
public class UpdatePlayerRequest {

    @Schema(description = "New name of the player", example = "NewJason", required = true)
    private String name;
}
