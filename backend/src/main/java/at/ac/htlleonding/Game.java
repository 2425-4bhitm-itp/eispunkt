package at.ac.htlleonding;

import java.time.LocalDate;


public class Game {
    private int gameId;
    private LocalDate date;

    public Game(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    private void setDate(LocalDate date) {
        this.date = date;
    }

    public int getGameId() {
        return gameId;
    }
}
