package at.ac.htlleonding.entities;

import jakarta.persistence.*;

@Entity
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scoreId;

    @ManyToOne
    private Team team;
    @ManyToOne
    private Turn turn;
    private int score;

    public Score(Team team, Turn turn) {
        this.team = team;
        this.turn = turn;
    }

    public Score() {
    }

    public Long getScoreId() {
        return scoreId;
    }

    private void setScoreId(Long scoreId) {
        this.scoreId = scoreId;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Turn getTurn() {
        return turn;
    }

    public void setTurn(Turn turn) {
        this.turn = turn;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
