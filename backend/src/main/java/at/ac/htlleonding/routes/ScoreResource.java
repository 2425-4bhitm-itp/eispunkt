package at.ac.htlleonding.routes;

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
    
    @Inject
    TeamRepository teamRepository;
    
    @Inject
    TurnRepository turnRepository;

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

    @POST
    @Path("/create")
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public Response createScoreWithIds(@QueryParam("teamId") long teamId, 
                                       @QueryParam("turnId") long turnId, 
                                       @QueryParam("score") int scoreValue) {
        if (teamId == 0 || turnId == 0) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        
        var team = teamRepository.findById(teamId);
        var turn = turnRepository.findById(turnId);
        
        if (team == null || turn == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        
        Score score = scoreRepository.create(team, turn);
        score.setScore(scoreValue);
        scoreRepository.persistAndFlush(score);
        
        return Response.status(Response.Status.CREATED).entity(score).build();
    }

    @GET
    @Path("{id:[0-9]+}")
    public Response findById(@PathParam("id") long scoreId) {
        if (scoreId == 0) {
            return Response.ok(scoreRepository.findAll()).build();
        } else {
            return Response.ok(scoreRepository.findById(scoreId)).build();
        }
    }

    @PUT
    @Transactional
    public Response updateScore(Score score) {
        if (score == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        scoreRepository.updateScore(score);
        return Response.ok().build();
    }

    @DELETE
    @Path("{id:[0-9]+}")
    public Response deleteScore(@PathParam("id") long scoreId) {
        if (scoreId == 0) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        scoreRepository.deleteById(scoreId);
        return Response.noContent().build();
    }
}
