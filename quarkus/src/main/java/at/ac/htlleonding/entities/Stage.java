package at.ac.htlleonding.entities;

import jakarta.persistence.*;

@Entity
public class Stage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long stageId;

    @ManyToOne
    @JoinColumn
    public Game game;

    public int stageNumber;

    public Stage() {
    }

    public Stage(Game game, int stageNumber) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    private void setGame(Game game) {
        this.game = game;
    }

    public long getStageId() {
        return stageId;
    }

    private void setStageId(long stageId) {
        this.stageId = stageId;
    }

    public int getStageNumber() {
        return stageNumber;
    }

    public void setStageNumber(int stageNumber) {
        this.stageNumber = stageNumber;
    }
}
