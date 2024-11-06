package at.ac.htlleonding.routes;

import io.quarkus.qute.runtime.ContentTypes;
import jakarta.ws.rs.*;
import at.ac.htlleonding.model.Team;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.annotations.Query;


@Path("/team")
public class TeamResource {

    @Path("/addTeam")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addTeam(@QueryParam("teamName") String name){
        GameResource.currentGame.getTeams().add(new Team(name));
        return Response.ok().build();
    }
}