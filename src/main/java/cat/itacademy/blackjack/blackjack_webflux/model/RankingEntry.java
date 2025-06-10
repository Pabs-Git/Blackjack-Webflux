package cat.itacademy.blackjack.blackjack_webflux.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RankingEntry {

    private Long playerId;
    private String name;
    private int wins;
    private int games;
}
