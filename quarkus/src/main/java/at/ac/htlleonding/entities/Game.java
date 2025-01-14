package at.ac.htlleonding.entities;

import jakarta.enterprise.inject.Default;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long gameId;
    @NotNull
    public LocalDate date;
    @OneToMany
    @JoinColumn
    public List<Team> teams = new LinkedList<>();
    @OneToMany
    @JoinColumn
    public List<Stage> stages = new LinkedList<>();

    public Game() {
        date = LocalDate.now();
    }

    public Game(LocalDate date) {
        this.date = date;
    }
}
