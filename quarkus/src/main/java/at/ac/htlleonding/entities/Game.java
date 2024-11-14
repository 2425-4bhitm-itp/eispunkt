package at.ac.htlleonding.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long gameId;
    @NotNull
    LocalDate date;
    @OneToMany
    @JoinColumn
    List<Team> teams = new LinkedList<>();
    @OneToMany
    @JoinColumn
    List<Stage> stages = new LinkedList<>();

    public Game() {
    }
}
