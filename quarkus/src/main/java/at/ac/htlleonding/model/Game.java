package at.ac.htlleonding.model;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;


public class Game {
    private LocalDate date;
    private List<Team> teams = new LinkedList<>();

    public Game(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    private void setDate(LocalDate date) {
        this.date = date;
    }


    public List<Team> getTeams() {
        return new LinkedList<>(teams);
    }

    public void addTeam(Team team) {
        teams.add(team);
    }
}
