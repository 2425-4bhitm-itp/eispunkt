package at.ac.htlleonding.entities;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;

@Entity
public class Stage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long stageId;

    @ManyToOne
    @JsonbTransient
    public Game game;

    @Column(name = "stage_number")
    public int stageNumber;

    public Stage() {
    }

    public Stage(Game game, int stageNumber) {
        setGame(game);
        setStageNumber(stageNumber);
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public long getStageId() {
        return stageId;
    }

    public void setStageId(long stageId) {
        this.stageId = stageId;
    }

    public int getStageNumber() {
        return stageNumber;
    }

    public void setStageNumber(int stageNumber) {
        this.stageNumber = stageNumber;
    }
}
