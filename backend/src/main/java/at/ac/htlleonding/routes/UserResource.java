package at.ac.htlleonding.routes;

import at.ac.htlleonding.entities.Team;
import at.ac.htlleonding.repositories.TeamRepository;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;

@Path("/api/users")
@Authenticated
public class UserResource {
    @Inject
    JsonWebToken jwt;

    @Inject
    TeamRepository teamRepository;

    @POST
    @Path("/init")
    @Transactional
    public Response initUser(){
        String username = jwt.getClaim("preferred_username");

        Team team = teamRepository.findByName(username);

        if(team == null){
            team = new Team(username);
            teamRepository.persist(team);
        }

        return Response.ok(team).build();
    }
}