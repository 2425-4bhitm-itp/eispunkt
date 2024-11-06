package at.ac.htlleonding.model;

public class Score {
    private Team team;
    private Stage stage;
    private int score;

    public Score(Stage stage, Team team) {
        this.stage = stage;
        this.team = team;
    }

    public Team getTeam() {
        return team;
    }

    private void setTeam(Team team) {
        this.team = team;
    }

    public Game getGame() {
        return stage;
    }

    private void setGame(Stage stage) {
        this.stage = stage;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
