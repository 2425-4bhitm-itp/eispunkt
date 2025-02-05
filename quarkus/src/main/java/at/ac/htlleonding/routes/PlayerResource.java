package at.ac.htlleonding.routes;

import at.ac.htlleonding.entities.Team;
import at.ac.htlleonding.repositories.PlayerRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

@Path("/api/players")
public class PlayerResource {

    @Inject
    PlayerRepository playerRepository;

    @Path("/createPlayer")
    @GET
    @Transactional
    public Response createPlayer(@QueryParam("name") String name) {
        return Response.ok(playerRepository.createPlayer(name)).build();
    }

    @Path("/findById")
    @GET
    public Response findById(
            @QueryParam("playerId") long playerId
    ){
        return Response.ok(playerRepository.findById(playerId)).build();
    }

    @Path("/createPlayerIntoTeam")
    @GET
    @Transactional
    public Response createPlayerIntoTeam(
            @QueryParam("teamId")Team team,
            @QueryParam("name") String name
    ){
        return Response.ok(playerRepository.createPlayerIntoTeam(team, name)).build();
    }

    @Path("/getTeamOfPlayer")
    @GET
    public Response getTeamOfPlayer(
            @QueryParam("playerId") long playerId
    ) {
        return Response.ok(playerRepository.getTeamOfPlayer(playerRepository.findById(playerId))).build();
    }

    @Path("/setTeamOfPlayer")
    @GET
    @Transactional
    public Response setTeamOfPlayer(
            @QueryParam("playerId") long playerId,
            @QueryParam("teamId") Team team
    ) {
        return Response.ok(playerRepository.setTeamOfPlayer(playerRepository.findById(playerId), team)).build();
    }

    @Path("/getAllPlayersWithTeam")
    @GET
    public Response getAllPlayersWithTeam(
            @QueryParam("teamId") long teamId
    ) {
        return Response.ok(playerRepository.getAllPlayersWithTeam(teamId)).build();
    }
}