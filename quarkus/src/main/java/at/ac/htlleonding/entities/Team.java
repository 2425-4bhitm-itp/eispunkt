package at.ac.htlleonding.entities;

import jakarta.persistence.*;

import java.util.LinkedList;
import java.util.List;

@Entity
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long teamId;
    public String name;
    @OneToMany
    @JoinColumn
    public List<Player> players = new LinkedList<>();


    @Override
    public String toString() {
        return "Team{" +
                "teamId=" + teamId +
                ", name='" + name + '\'' +
                ", players=" + players +
                '}';
    }
}