package cat.itacademy.blackjack.blackjack_webflux.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Card {

    private String suit;
    private String rank;
}
