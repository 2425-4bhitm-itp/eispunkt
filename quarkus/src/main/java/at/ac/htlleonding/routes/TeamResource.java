package at.ac.htlleonding.routes;

import at.ac.htlleonding.entities.Player;
import at.ac.htlleonding.entities.Team;
import at.ac.htlleonding.repositories.PlayerRepository;
import at.ac.htlleonding.repositories.TeamRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
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
        return Response.ok(teamRepository.getAllTeams()).build();
    }
}
