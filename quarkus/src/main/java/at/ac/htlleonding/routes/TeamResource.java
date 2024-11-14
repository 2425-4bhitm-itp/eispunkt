/* at.ac.htlleonding.routes;

import at.ac.htlleonding.repositories.TeamRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import at.ac.htlleonding.entities.Team;
import jakarta.ws.rs.Path;

import java.util.List;


@Path("/teams")
public class TeamResource {

    @Inject
    TeamRepository teamRepository;
    /*@Path("/addTeam")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addTeam(@QueryParam("teamName") String name){
        GameResource.currentGame.getTeams().add(new Team(name));
        return Response.ok().build();
    }

import at.ac.htlleonding.entities.Team;
import jakarta.ws.rs.GET;

import java.util.List;

@GET
    public List<Team> allTeams() {
        return teamRepository.listAll();
    }
}
*/