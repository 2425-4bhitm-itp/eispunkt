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


    @Path("/findTeamById")
    @GET
    public Response findTeamById(@QueryParam("teamId") long teamId){
        return Response.ok(teamRepository.findById(teamId)).build();
    }

    @Path("/findTeamByName")
    @GET
    public Response findTeamByName(@QueryParam("teamName") String teamName){
        return Response.ok(teamRepository.findByName(teamName)).build();
    }

    @Path("/getAllTeams")
    @GET
    public Response getAllTeams(){
        return Response.ok(teamRepository.getAllTeams()).build();
    }

    @Path("/getAllGamesOfTeam")
    @GET
    public Response getAllPlayersOfTeam(@QueryParam("teamId") long teamId){
        return Response.ok(teamRepository.getAllGamesOfTeam(teamId)).build();
    }

    @Path("/renameTeam")
    @POST
    @Transactional
    public Response renameTeam(@QueryParam("teamId") long teamId, @QueryParam("newName") String newName){
        teamRepository.renameTeam(teamId, newName);
        return Response.ok().build();
    }
}
