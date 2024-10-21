package at.ac.htlleonding;

public class Team {
    private int teamId;
    private String name;
    private int score;

    public Team(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public int getTeamId() {
        return teamId;
    }

    public int getScore() {
        return score;
    }

    private void setScore(int score) {
        this.score = score;
    }

    public void addScore(int score) {
        this.score += score;
    }

}
