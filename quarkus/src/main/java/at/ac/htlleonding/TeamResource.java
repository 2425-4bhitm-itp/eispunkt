package at.ac.htlleonding;

import io.quarkus.qute.runtime.ContentTypes;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.annotations.Query;


@Path("/team")
public class TeamResource {

    @Path("/addTeam")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addTeam(@QueryParam("teamName") String name){
        /*if(GameResource.currentGame.getTeams().size() == 2){
            GameResource.currentGame.getTeams().clear();
        }*/
        GameResource.currentGame.getTeams().add(new Team(name));
        return Response.ok().build();
    }
}