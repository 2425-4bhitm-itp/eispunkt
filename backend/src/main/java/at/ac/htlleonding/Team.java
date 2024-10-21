package at.ac.htlleonding;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Team {
    private int teamId;
    private String name;
    private int score;
    private List<Game> games = new LinkedList<>();

    public Team(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public int getTeamId() {
        return teamId;
    }

    public int getScore() {
        return score;
    }

    private void setScore(int score) {
        this.score = score;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public List<Game> getGames() {
        return new LinkedList<>(games);
    }

    public void addGame(Game game) {
        games.add(game);
    }


}
