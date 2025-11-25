package at.ac.htlleonding.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gameId;

    @NotNull
    private LocalDate date;

    @ManyToMany
    private List<Team> teams = new LinkedList<>();

    // Constructors, getters, and setters
    public Game() {
        date = LocalDate.now();
    }

    public Game(LocalDate date) {
        this.date = date;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean addTeam(Team team) {
        return teams.add(team);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return Objects.equals(teams, game.teams);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameId, teams);
    }
}