package cat.itacademy.blackjack.blackjack_webflux.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request to perform an action in an existing game")
public class PlayRequest {

    @Schema(description = "Type of action (HIT, STAND, DOUBLE_DOWN)", example = "HIT", required = true)
    private String action;

    @Schema(description = "Amount bet in this move", example = "10.5", required = true)
    private double bet;
}
