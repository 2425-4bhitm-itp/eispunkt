package at.ac.htlleonding.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Tournament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tournamentId;
    private String name;
    private String location;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "tournament_team", joinColumns = @JoinColumn(name = "tournamentId"),
            inverseJoinColumns = @JoinColumn(name = "teamId"))
    @JsonIgnoreProperties({"tournaments"})
    @JsonIgnore
    private List<Team> teams = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "tournament_game", joinColumns = @JoinColumn(name = "tournamentId"),
            inverseJoinColumns = @JoinColumn(name = "gameId"))
    @JsonIgnoreProperties({"tournaments"})
    private List<Game> games = new ArrayList<>();

    public Tournament() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(long groupId) {
        this.tournamentId = groupId;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    public void addGame(Game game) {
        games.add(game);
    }

    public void addTeam(Team team) {
        teams.add(team);
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
