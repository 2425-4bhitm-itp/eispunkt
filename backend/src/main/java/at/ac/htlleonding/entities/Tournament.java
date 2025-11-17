package at.ac.htlleonding.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

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

    public List<String> generateGames() {
        List<String> gameplan = new LinkedList<>();

        int numRounds = teams.size() - 1;

        for (int round = 0; round < numRounds; round++) {
            for (int i = 0; i < teams.size() / 2; i++) {
                Team team1 = teams.get(i);
                Team team2 = teams.get(teams.size() - 1 - i);

                if (team2 != null) {
                    Game game = new Game();
                    game.addTeam(team1);
                    game.addTeam(team2);

                    gameplan.add(team1.getName() + " vs " + team2.getName());
                } else {
                    gameplan.add(team1.getName() + " gets a break!");
                }
                Team lastTeam = teams.removeLast();

                teams.add(1, lastTeam);
            }
        }
        return gameplan;
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

    public boolean addTeam(Team team) {
        return teams.add(team);
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
