package at.ac.htlleonding.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "E_GROUP")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupId;
    private String name;
    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<Team> teams = new ArrayList<>();
    @OneToMany
    private List<Game> games = new ArrayList<>();

    public Group() {
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

    public long getGroupId() {
        return groupId;
    }

    private void setGroupId(long groupId) {
        this.groupId = groupId;
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
}
