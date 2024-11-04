package at.ac.htlleonding;

public class Score {
    private Team team;
    private Game game;
    private int score;

    public Score(Game game, Team team) {
        this.game = game;
        this.team = team;
    }

    public Team getTeam() {
        return team;
    }

    private void setTeam(Team team) {
        this.team = team;
    }

    public Game getGame() {
        return game;
    }

    private void setGame(Game game) {
        this.game = game;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
