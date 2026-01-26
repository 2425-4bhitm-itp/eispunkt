package at.ac.htlleonding.routes;

import at.ac.htlleonding.dto.TurnCreationDTO;
import at.ac.htlleonding.entities.Stage;
import at.ac.htlleonding.entities.Team;
import at.ac.htlleonding.entities.Turn;
import at.ac.htlleonding.repositories.TeamRepository;
import at.ac.htlleonding.repositories.TurnRepository;
import at.ac.htlleonding.repositories.StageRepository;
import at.ac.htlleonding.websocket.ScoreWebSocket;
import at.ac.htlleonding.websocket.TurnWebSocket;
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
    @Inject
    TurnWebSocket turnWebSocket;
    @Inject
    TeamRepository teamRepository;
    @Inject
    ScoreWebSocket scoreWebSocket;

    @POST
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createTurn(TurnCreationDTO turnDto) {
        if (turnDto == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
            Turn turn = new Turn();

            Stage stage = stageRepository.findById(turnDto.stageId());
            if (stage == null) {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("Stage not found").build();
            }
            turn.setStage(stage);

            var team = teamRepository.findById(turn.getTeam().getTeamId());
            if (team == null) {
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("Team not found").build();
            }
            turn.setTeam(team);

        turnRepository.persistAndFlush(turn);
        turnWebSocket.broadcastTurn(turn);
        scoreWebSocket.broadcastScore(turn.getStage().getGame());

        var location = UriBuilder.fromResource(TurnResource.class)
                                 .path(String.valueOf(turn.getTurnId()))
                                 .build();

        return Response.status(Response.Status.CREATED)
                       .location(location)
                       .entity(turn)
                       .build();
    }

    @GET
    @Path("{id:[0-9]+}")
    public Response getTurn(@PathParam("id") long turnId) {
        if (turnId == 0) {
            return Response.ok(turnRepository.findAll()).build();
        } else {
            return Response.ok(turnRepository.findById(turnId)).build();
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
        turnWebSocket.broadcastTurn(turn);
        scoreWebSocket.broadcastScore(turn.getStage().getGame());
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