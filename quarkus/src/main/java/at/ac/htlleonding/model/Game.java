package at.ac.htlleonding.model;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;


public class Game {
    private final LocalDate date;
    private final List<Team> teams = new LinkedList<>();
    private List<Stage> stages = new LinkedList<>();

    public Game(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public List<Team> getTeams() {
        return new LinkedList<>(teams);
    }

    public void addTeam(Team team) {
        teams.add(team);
    }

    public List<Stage> getStages() {
        return stages;
    }

    private void setStages(List<Stage> stages) {
        this.stages = stages;
    }

    public void addStage(Stage stage) {
        stages.add(stage);
    }

    private void removeStage(Stage stage) {
        stages.remove(stage);
    }

    private void resetStages() {
        stages.clear();
    }

    public void setScoreOfTeam(Team team, int value) {
        stages.getLast().setScoreOfTeam(team, value);
    }
}
