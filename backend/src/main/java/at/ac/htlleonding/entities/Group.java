package at.ac.htlleonding.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "E_GROUP")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupId;

    @ManyToMany
    private List<Team> teams = new ArrayList<>();

    @OneToMany
    private List<Game> games = new ArrayList<>();

    public Group() {
    }

    public void generateGames() {
        // TODO: Insert game generation
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
