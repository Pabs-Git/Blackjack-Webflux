package cat.itacademy.blackjack.blackjack_webflux.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document("games")
public class Game {

    @Id
    private String id;
    private Long playerId;
    private String playerName;
    @Builder.Default
    private List<Card> deck = new ArrayList<>();
    @Builder.Default
    private int score = 0;
    @Builder.Default
    private GameStatus status = GameStatus.IN_PROGRESS;
}
