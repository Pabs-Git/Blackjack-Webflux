package cat.itacademy.blackjack.blackjack_webflux.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("players")
public class Player {

    @Id
    private Long id;
    private String name;
    private int totalWins;
    private int totalGames;
}
