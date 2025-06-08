package cat.itacademy.blackjack.blackjack_webflux.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("players")
public class Player {

    @Id
    private Long id;
    private String name;
    private int totalWins;
    private int totalGames;

    public Player() {
    }

    public Player(Long id, String name, int totalWins, int totalGames) {
        this.id = id;
        this.name = name;
        this.totalWins = totalWins;
        this.totalGames = totalGames;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalWins() {
        return totalWins;
    }

    public void setTotalWins(int totalWins) {
        this.totalWins = totalWins;
    }

    public int getTotalGames() {
        return totalGames;
    }

    public void setTotalGames(int totalGames) {
        this.totalGames = totalGames;
    }
}
