package at.ac.htlleonding.routes;

import at.ac.htlleonding.repositories.PlayerRepository;
import at.ac.htlleonding.repositories.TeamRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

@Path("/api/players")
public class PlayerResource {

    @Inject
    PlayerRepository playerRepository;
    @Inject
    TeamRepository teamRepository;

    @Path("/createPlayer")
    @GET
    @Transactional
    public Response createPlayer(@QueryParam("name") String name) {
        return Response.ok(playerRepository.createPlayer(name)).build();
    }

    @Path("/findById")
    @GET
    public Response findById(@QueryParam("playerId") long playerId) {
        return Response.ok(playerRepository.findById(playerId)).build();
    }

    @Path("/createPlayerIntoTeam")
    @GET
    @Transactional
    public Response createPlayerIntoTeam(@QueryParam("teamId") long teamId, @QueryParam("name") String name) {
        return Response.ok(playerRepository.createPlayerIntoTeam(teamRepository.findById(teamId), name)).build();
    }

    @Path("/getTeamOfPlayer")
    @GET
    public Response getTeamOfPlayer(@QueryParam("playerId") long playerId) {
        return Response.ok(playerRepository.getTeamOfPlayer(playerRepository.findById(playerId))).build();
    }

    @Path("/setTeamOfPlayer")
    @GET
    @Transactional
    public Response setTeamOfPlayer(@QueryParam("playerId") long playerId, @QueryParam("teamId") long teamId) {
        return Response.ok(playerRepository.setTeamOfPlayer(
                                                            playerRepository.findById(playerId),
                                                            teamRepository.findById(teamId)
                                                           )).build();
    }

    @Path("/getAllPlayersOfTeam")
    @GET
    public Response getAllPlayersOfTeam(@QueryParam("teamId") long teamId) {
        return Response.ok(playerRepository.getAllPlayersWithTeam(teamId)).build();
    }

    @Path("/renamePlayer")
    @POST
    @Transactional
    public Response renamePlayer(@QueryParam("playerId") long playerId, @QueryParam("newName") String newName) {
        playerRepository.renamePlayer(playerId, newName);
        return Response.ok().build();
    }
}