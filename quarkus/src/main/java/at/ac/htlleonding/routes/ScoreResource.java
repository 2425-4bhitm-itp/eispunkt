package at.ac.htlleonding.routes;

import at.ac.htlleonding.entities.Score;
import at.ac.htlleonding.entities.Team;
import at.ac.htlleonding.entities.Turn;
import at.ac.htlleonding.repositories.ScoreRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("api/scores")
public class ScoreResource {
    @Inject
    ScoreRepository scoreRepository;

    @Path("create")
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createScore(@QueryParam("team") Team team, @QueryParam("turn") Turn turn) {
        Score score = scoreRepository.create(team, turn);

        return Response.ok(score).build();
    }

    @Path("create")
    public Response createScore() {
        return Response.ok(scoreRepository.create()).build();
    }

    @Path("findById")
    public Response findById(@QueryParam("scoreId") long scoreId) {
        return Response.ok(scoreRepository.findById(scoreId)).build();
    }

    @Path("update")
    public Response updateScore(@QueryParam("scoreId") long scoreId, int scoreValue) {
        Score score = scoreRepository.findById(scoreId);

        scoreRepository.updateScore(score, scoreValue);

        return Response.ok(score).build();
    }

    @Path("getScore")
    public Response getScore(@QueryParam("scoreId") long scoreId) {
        return Response.ok(scoreRepository.getScore(scoreRepository.findById(scoreId))).build();
    }
}
