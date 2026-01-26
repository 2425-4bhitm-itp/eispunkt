package at.ac.htlleonding.dto;

import at.ac.htlleonding.entities.Game;
import at.ac.htlleonding.entities.Stage;
import at.ac.htlleonding.entities.Team;
import at.ac.htlleonding.entities.Turn;

import java.util.List;

public record Score(Long gameId, int team1Score, int team2Score) {
    public static Score createFromGame(Game game) {
        List<Stage> stages = game.getStages();

        int team1Score = 0;
        int team2Score = 0;


        for (Stage stage : stages) {
            int team1Points = 0;
            int team2Points = 0;
            List<Turn> turns = stage.getTurns();
            for (Turn turn : turns) {
                Team team = turn.getTeam();

                if (team.equals(game.getTeams().get(0))) {
                    team1Points += turn.getScore();
                } else if (team.equals(game.getTeams().get(1))) {
                    team2Points += turn.getScore();
                }
            }

            if (team1Points > team2Points) {
                team1Score += 2;
            } else if (team2Points > team1Points) {
                team2Score += 2;
            } else {
                team1Score++;
                team2Score++;
            }
        }

        return new Score(game.getGameId(), team1Score, team2Score);
    }
}
