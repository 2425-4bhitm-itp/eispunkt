package at.ac.htlleonding.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playerId;
    private String name;

    public Player(String name) {
        setName(name);
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
}
