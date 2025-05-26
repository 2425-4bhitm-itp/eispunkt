package at.ac.htlleonding.routes;

import at.ac.htlleonding.entities.Group;
import at.ac.htlleonding.entities.Score;
import at.ac.htlleonding.repositories.ScoreRepository;
import at.ac.htlleonding.repositories.TeamRepository;
import at.ac.htlleonding.repositories.TurnRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

@Path("api/scores")
public class ScoreResource {
    @Inject
    ScoreRepository scoreRepository;

    @POST
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createScore(Score score) {
        Response.ResponseBuilder response;
        if (score == null) {
            response = Response.status(Response.Status.BAD_REQUEST);
        } else {
            scoreRepository.persistAndFlush(score);
            var location = UriBuilder.fromResource(ScoreResource.class)
                                     .path(String.valueOf(score.getScoreId()))
                                     .build();
            response = Response.status(Response.Status.CREATED).location(location);
        }
        return response.build();
    }

    @GET
    @Path("{id:[0-9]+")
    public Response findById(@PathParam("id") long scoreId) {
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
