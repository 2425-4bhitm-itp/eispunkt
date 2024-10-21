package at.ac.htlleonding;

public class Player {
    private int playerId;
    private String name;
    private Team team;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public int getPlayerId() {
        return playerId;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
