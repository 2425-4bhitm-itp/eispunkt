package at.ac.htlleonding.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Entity
public class Turn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long turnId;

    @ManyToOne
    private Stage stage;

    @Column(name = "turn_number")
    private int turnNumber;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @Min(message = "Score must be between 1 and 4", value = 1)
    @Max(message = "Score must be between 1 and 4", value = 4)
    private int score;

    public Turn() {
    }

    public Turn(Stage stage) {
        setStage(stage);
    }

    public long getTurnId() {
        return turnId;
    }

    private void setTurnId(long turnId) {
        this.turnId = turnId;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public void setTurnNumber(int turnNumber) {
        this.turnNumber = turnNumber;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
