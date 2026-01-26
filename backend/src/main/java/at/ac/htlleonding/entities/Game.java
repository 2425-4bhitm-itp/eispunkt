package at.ac.htlleonding.entities;

import jakarta.json.bind.annotation.JsonbTransient;
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

    @OneToMany(mappedBy = "game")
    @JsonbTransient
    private List<Stage> stages = new LinkedList<>();

    @Column(name = "is_active", columnDefinition = "boolean default false")
    private Boolean isActive = true;

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

    public List<Stage> getStages() {
        return stages;
    }

    public void setStages(List<Stage> stages) {
        this.stages = stages;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
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