package at.ac.htlleonding.entities;

import jakarta.persistence.*;

import java.util.LinkedList;
import java.util.List;

@Entity
public class Turn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long turnId;
    @OneToMany
    @JoinColumn
    private final List<Score> scores = new LinkedList<>();
    @ManyToOne
    private Stage stage;
}
