package at.ac.htlleonding.routes;

import at.ac.htlleonding.entities.Score;
import at.ac.htlleonding.repositories.ScoreRepository;
import at.ac.htlleonding.repositories.TeamRepository;
import at.ac.htlleonding.repositories.TurnRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

@Path("api/scores")
public class ScoreResource {
    @Inject
    ScoreRepository scoreRepository;

    @Inject
    TeamRepository teamRepository;

    @Inject
    TurnRepository turnRepository;

    @Path("create")
    @Transactional
    public Response createScore(@QueryParam("teamId") long teamId, @QueryParam("turnId") long turnId) {
        Score score = scoreRepository.create(teamRepository.findById(teamId), turnRepository.findById(turnId));

        return Response.ok(score).build();
    }

    @PUT
    @Path("create")
    public Response createScore() {
        return Response.ok(scoreRepository.create()).build();
    }

    @GET
    @Path("findById")
    public Response findById(@QueryParam("scoreId") long scoreId) {
        return Response.ok(scoreRepository.findById(scoreId)).build();
    }

    @PUT
    @Path("update")
    public Response updateScore(@QueryParam("scoreId") long scoreId, int scoreValue) {
        Score score = scoreRepository.findById(scoreId);
        scoreRepository.updateScore(score, scoreValue);

        return Response.ok(score).build();
    }

    @GET
    @Path("getScore")
    public Response getScore(@QueryParam("scoreId") long scoreId) {
        return Response.ok(scoreRepository.getScore(scoreRepository.findById(scoreId))).build();
    }

    @GET
    @Path("getAllScores")
    public Response getAllScores() {
        return Response.ok(scoreRepository.getAllScores()).build();
    }
}
