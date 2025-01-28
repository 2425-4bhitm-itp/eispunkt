package at.ac.htlleonding.routes;

import at.ac.htlleonding.entities.Score;
import at.ac.htlleonding.entities.Team;
import at.ac.htlleonding.entities.Turn;
import at.ac.htlleonding.repositories.ScoreRepository;
import at.ac.htlleonding.repositories.TeamRepository;
import at.ac.htlleonding.repositories.TurnRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

@Path("api/scores")
public class ScoreResource {
    @Inject
    TurnRepository turnRepository;

    @Inject
    TeamRepository teamRepository;

    @Inject
    ScoreRepository scoreRepository;

    @Path("/createScore")
    public Response createScore(
            @QueryParam("turnId") long turnId,
            @QueryParam("teamId") long teamId) {
        Turn turn = turnRepository.findById(turnId);
        Team team = teamRepository.findById(teamId);
        Score newScore = new Score();
        scoreRepository.persist(newScore);
        turn.scores.add(newScore);
        newScore.team = team;
        return Response.ok(newScore.scoreId).build();
    }

    @Path("/updateScore")
    public Response updateScore(
            @QueryParam("scoreId") long scoreId,
            @QueryParam("points") int points) {
        Score score = scoreRepository.findById(scoreId);
        score.score = points;
        return Response.ok().build();
    }

}
