package at.ac.htlleonding.model;

import java.util.List;

public class Stage {
    private static final int MAX_SCORES = 2;
    private final Score[] scores = new Score[MAX_SCORES];

    public Stage(Game game, List<Team> teams) {
        for (int i = 0; i < MAX_SCORES; i++) {
            scores[i] = new Score(this, teams.get(i));
        }
    }

    public void setScoreOfTeam(Team team, int value) {
        for (int i = 0; i < MAX_SCORES; i++) {
            if (scores[i].getTeam().equals(team)) {
                scores[i].setScore(value);
                return;
            }
        }
    }
}
