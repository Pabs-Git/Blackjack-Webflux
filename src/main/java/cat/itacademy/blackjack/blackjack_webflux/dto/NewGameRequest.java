package cat.itacademy.blackjack.blackjack_webflux.dto;

/**
 * DTO for creating a new game request.
 */
public class NewGameRequest {

    private String playerName;

    public NewGameRequest() {
    }

    public NewGameRequest(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
