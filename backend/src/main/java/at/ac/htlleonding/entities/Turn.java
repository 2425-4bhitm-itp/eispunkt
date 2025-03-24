package at.ac.htlleonding.entities;

import jakarta.persistence.*;

@Entity
public class Turn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long turnId;

    @ManyToOne
    private Stage stage;

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

    private void setStage(Stage stage) {
        this.stage = stage;
    }
}
