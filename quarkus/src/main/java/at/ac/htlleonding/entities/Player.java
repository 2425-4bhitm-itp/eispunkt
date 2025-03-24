package at.ac.htlleonding.entities;

import jakarta.persistence.*;

@Entity
@NamedQueries({@NamedQuery(name = "Player.getAllPlayersWithTeam", query = """
        select p from Player p where p.team.teamId = :teamId
        order by playerId
        """)})
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playerId;
    private String name;

    @ManyToOne
    private Team team;

    public Player(String name) {
        setName(name);
    }

    public Player(Team team, String name) {
        this.team = team;
        this.name = name;
    }

    public Player() {
    }

    public Long getPlayerId() {
        return playerId;
    }

    private void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public String getName() {
        return name;
    }


    private void setName(String name) {
        if (!(name == null || name.isBlank())) {
            this.name = name;
        }
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        if (!(name == null || name.isBlank())) {
            this.team = team;
        }
    }
}
