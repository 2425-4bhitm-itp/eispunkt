package at.ac.htlleonding.dto;

import at.ac.htlleonding.entities.Game;
import at.ac.htlleonding.entities.Stage;
import at.ac.htlleonding.entities.Team;
import at.ac.htlleonding.entities.Turn;

import java.util.List;

public record ActiveGameDTO(Long gameId, Team team1, Team team2, int team1Score, int team2Score, Stage currentStage) {
    public static ActiveGameDTO createFromGame(Game game) {
        List<Stage> stages = game.getStages();

        int team1Score = 0;
        int team2Score = 0;

        Team team1 = game.getTeams().getFirst();
        Team team2 = game.getTeams().getLast();

        for (Stage stage : stages) {
            List<Turn> turns = stage.getTurns();

            if (turns.size() != 6) {
                continue;
            }

            int team1Points = 0;
            int team2Points = 0;

            for (Turn turn : turns) {
                if (turn.getTeam().equals(team1)) {
                    team1Points += turn.getScore();
                } else if (turn.getTeam().equals(team2)) {
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

        return new ActiveGameDTO(
                game.getGameId(),
                team1,
                team2,
                team1Score,
                team2Score,
                game.getStages().getLast()
        );
    }

}
