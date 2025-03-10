package at.ac.htlleonding.routes;

import at.ac.htlleonding.entities.Player;
import at.ac.htlleonding.entities.Team;
import at.ac.htlleonding.repositories.PlayerRepository;
import at.ac.htlleonding.repositories.TeamRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@Path("/api/team")
public class TeamResource {
    @Inject
    TeamRepository teamRepository;

    @Inject
    PlayerRepository playerRepository;

    @Path("/createTeam")
    @GET
    @Transactional
    public Response createTeam(@QueryParam("teamName") String teamName) {
        if(teamName == null || teamName.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }else{
            return Response.ok(teamRepository.createTeam(teamName)).build();
        }
    }

    @Path("/addPlayer")
    @GET
    @Transactional
    public Response addPlayer(@QueryParam("teamId") long teamId, @QueryParam("playerId") long playerId) {
        if(teamId == 0 || playerId == 0) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }else{
            teamRepository.addPlayerToTeam(teamId, playerId);
            return Response.ok("Player added Successfully").build();
        }
    }

    @Path("/findTeamId")
    @GET
    public Response findTeamById(@QueryParam("teamId") long teamId){
        return Response.ok(teamRepository.findById(teamId)).build();
    }

    @Path("/findTeam")
    @GET
    public Response findTeamByName(@QueryParam("teamName") String teamName){
        return Response.ok(teamRepository.findByName(teamName)).build();
    }

    @Path("/getAllTeams")
    @GET
    public Response getAllTeams(){
        return Response.ok(teamRepository.listAll()).build();
    }

    @Path("/createTeamWithPlayers")
    @POST
    @Transactional
    public Response createTeamWithPlayers(String name, long... playerIds) {
        if (!(name == null || name.isBlank())) {
            return Response.ok(teamRepository.createTeamWithPlayers(name, playerIds)).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @Path("/updateTeam")
    @PUT
    @Transactional
    public Response updateTeam(@QueryParam("teamId") long teamId, @QueryParam("teamName") String name) {
        return Response.ok(teamRepository.update("""
                                update team
                                set name = ?1
                                where teamid = ?2
                                """, name, teamId)).build();
    }
}
