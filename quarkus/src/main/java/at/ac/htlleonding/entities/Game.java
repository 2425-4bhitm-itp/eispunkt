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

    public List<String> generateGameplan(){
        List<String> gameplan = new LinkedList<>();

        int numRounds = teams.size() -1;

        for (int round = 0; round < numRounds; round++) {
            for(int i = 0; i < teams.size()/2; i++){
                String team1Name = teams.get(i).name;
                String team2Name = teams.get(teams.size() - 1 - i).name;

                if(team2Name != null ){
                    gameplan.add(team1Name + " vs " + team2Name);
                }
                else{
                    gameplan.add(team1Name + " gets a break!");
                }

                Team lastTeam = teams.removeLast();
                teams.add(1, lastTeam);
            }
        }
        return gameplan;
    }
}
