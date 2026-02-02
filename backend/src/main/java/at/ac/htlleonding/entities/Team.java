package at.ac.htlleonding.entities;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamId;

    private String name;

    @ManyToMany(mappedBy = "teams")
    @JsonbTransient
    private List<Game> games = new LinkedList<>();

    @Column(name = "visible")
    private boolean isVisible = true;

    @OneToMany(mappedBy = "team")
    @JsonbTransient
    private List<Player> players = new ArrayList<>();

    public Team(String name) {
        this.name = name;
    }

    public Team() {
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (!(name == null || name.isBlank())) {
            this.name = name;
        }
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void addPlayer(Player player) {
        players.add(player);
        player.setTeam(this);
    }

    public void removePlayer(Player player) {
        players.remove(player);
        player.setTeam(null);
    }

}