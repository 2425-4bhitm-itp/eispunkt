package at.ac.htlleonding.routes;

import at.ac.htlleonding.entities.Game;
import at.ac.htlleonding.entities.Score;
import at.ac.htlleonding.entities.Stage;
import at.ac.htlleonding.entities.Turn;
import at.ac.htlleonding.repositories.GameRepository;
import at.ac.htlleonding.repositories.StageRepository;
import at.ac.htlleonding.repositories.TeamRepository;
import at.ac.htlleonding.repositories.TurnRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

@Path("/api/turns")
public class TurnResource {

    @Inject
    TurnRepository turnRepository;

    @Inject
    StageRepository stageRepository;

    @Inject
    TeamRepository teamRepository;

    @Inject
    GameRepository gameRepository;

    @Path("/newTurn")
    @Transactional
    public Response newTurn(@QueryParam("gameId") long gameId) {
        Turn turn = new Turn();
        Game game = gameRepository.findById(gameId);
        Stage stage = game.stages.get(0);
        stage.turn.add(turn);
        return Response.ok(turn.turnId).build();
    }

    @Path("/setScores")
    public Response addScores(@QueryParam("turnId") long turnId,
                              @QueryParam("team1Score") int team1Score, @QueryParam("team1Id") long team1Id,
                              @QueryParam("team2Score") int team2Score, @QueryParam("team2Id") long team2Id) {
        //dreckig ranzig reidig
        Score score1 = new Score();
        score1.team = teamRepository.findById(team1Id);
        score1.turn = turnRepository.findById(turnId);
        score1.score = team1Score;
        Score score2 = new Score();
        score2.team = teamRepository.findById(team2Id);
        score2.turn = turnRepository.findById(turnId);
        score2.score = team2Score;

        Turn turn = turnRepository.findById(turnId);
        turn.scores.add(score1);
        turn.scores.add(score2);

        return Response.ok().build();
    }
}
