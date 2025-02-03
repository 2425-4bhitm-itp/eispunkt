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

    public Stage() {
    }

    public Stage(Game game) {
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
}
