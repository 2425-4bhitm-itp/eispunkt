package at.ac.htlleonding.routes;

import at.ac.htlleonding.entities.Stage;
import at.ac.htlleonding.entities.Turn;
import at.ac.htlleonding.repositories.TurnRepository;
import at.ac.htlleonding.repositories.StageRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

@Path("/api/turns")
public class TurnResource {

    @Inject
    TurnRepository turnRepository;
    
    @Inject
    StageRepository stageRepository;

    @POST
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createTurn(Turn turn) {
        Response.ResponseBuilder response;
        if (turn == null) {
            response = Response.status(Response.Status.BAD_REQUEST);
        } else {
            turnRepository.persistAndFlush(turn);
            var location = UriBuilder.fromResource(TurnResource.class).path(String.valueOf(turn.getTurnId())).build();
            response = Response.status(Response.Status.CREATED).location(location).entity(turn);
        }
        return response.build();
    }

    @POST
    @Path("/create")
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTurnWithStageId(@QueryParam("stageId") long stageId) {
        if (stageId == 0) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        
        Stage stage = stageRepository.findById(stageId);
        if (stage == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        
        Turn turn = new Turn(stage);
        turnRepository.persistAndFlush(turn);
        
        return Response.status(Response.Status.CREATED).entity(turn).build();
    }

    @GET
    @Path("{id:[0-9]+}")
    public Response getTurn(@PathParam("id") long stageId) {
        if (stageId == 0) {
            return Response.ok(turnRepository.findAll()).build();
        } else {
            return Response.ok(turnRepository.findById(stageId)).build();
        }
    }

    @PUT
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateTurn(Turn turn) {
        if (turn == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        turnRepository.updateTurn(turn);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id:[0-9]+}")
    @Transactional
    public Response deleteTurn(@PathParam("id") long turnId) {
        if (turnId == 0) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        turnRepository.deleteById(turnId);
        return Response.noContent().build();
    }
}