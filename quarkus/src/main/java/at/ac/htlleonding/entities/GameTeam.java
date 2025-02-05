package at.ac.htlleonding.entities;
import jakarta.persistence.*;

@Entity
public class GameTeam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "gameid")
    private Game game;

    @ManyToOne
    @JoinColumn(name = "teamid")
    private Team team;

    // Constructors, getters, and setters
    public GameTeam() {}

    public GameTeam(Game game, Team team) {
        this.game = game;
        this.team = team;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}