package at.ac.htlleonding.model;

import java.util.LinkedList;
import java.util.List;

public class Team {
    private int teamId;
    private String name;
    private final List<Game> games = new LinkedList<>();

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

    public List<Game> getGames() {
        return new LinkedList<>(games);
    }

    public void addGame(Game game) {
        games.add(game);
    }
}