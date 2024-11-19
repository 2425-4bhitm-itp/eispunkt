package at.ac.htlleonding.entities;

import jakarta.persistence.*;

import java.util.LinkedList;
import java.util.List;

@Entity
public class Stage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long stageId;
    @OneToMany
    @JoinColumn
    private final List<Turn> turn = new LinkedList<>();
}
