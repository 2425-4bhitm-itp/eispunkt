package at.ac.htlleonding.routes;

import at.ac.htlleonding.entities.Player;
import at.ac.htlleonding.repositories.PlayerRepository;
import at.ac.htlleonding.repositories.TeamRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

@Path("/api/players")
public class PlayerResource {

    @Inject
    PlayerRepository playerRepository;
    @Inject
    TeamRepository teamRepository;

    @POST
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPlayer(Player player) {
        Response.ResponseBuilder response;
        if (player == null) {
            response = Response.status(Response.Status.BAD_REQUEST);
        } else {
            playerRepository.persistAndFlush(player);
            var location = UriBuilder.fromResource(PlayerResource.class)
                                     .path(String.valueOf(player.getPlayerId()))
                                     .build();
            response = Response.status(Response.Status.CREATED).location(location);
        }
        return response.build();
    }

    @Path("/{id:[0-9]+}")
    @GET
    public Response findById(@PathParam("id") long playerId) {
        if (playerId == 0) {
            return Response.ok(playerRepository.getAllPlayers()).build();
        }
        return Response.ok(playerRepository.findById(playerId)).build();
    }

    @Path("/team/{id:[0-9]+}")
    @GET
    public Response getAllPlayersOfTeam(@PathParam("id") long teamId) {
        return Response.ok(playerRepository.getAllPlayersWithTeam(teamId)).build();
    }

    @PUT
    @Transactional
    public Response updatePlayer(Player player) {
        if (player == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        playerRepository.updatePlayer(player);
        return Response.ok().build();
    }

    @Path("/{id:[0-9]+}")
    @PATCH
    @Transactional
    public Response setTeamOfPlayer(@PathParam("id") long playerId, @QueryParam("teamId") long teamId) {
        return Response.ok(playerRepository.setTeamOfPlayer(playerRepository.findById(playerId), teamRepository.findById(teamId)))
                       .build();
    }

    @Path("/rename/{id:[0-9]+}")
    @PATCH
    @Transactional
    public Response renamePlayer(@PathParam("id") long playerId, @QueryParam("newName") String newName) {
        playerRepository.renamePlayer(playerId, newName);
        return Response.ok().build();
    }

    @Path("/{id:[0-9]+}")
    @DELETE
    @Transactional
    public Response deletePlayer(@PathParam("id") long playerId) {
        if (playerId == 0) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        playerRepository.deleteById(playerId);
        return Response.noContent().build();
    }
}