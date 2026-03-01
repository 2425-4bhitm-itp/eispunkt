package at.ac.htlleonding.routes;

import at.ac.htlleonding.entities.Team;
import at.ac.htlleonding.repositories.TeamRepository;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
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
    public void initUser(){
        String username = jwt.getClaim("preferred_username");

        boolean teamExists = teamRepository.findByName(username) != null;

        if(!teamExists){
            Team team = new Team(username);
            teamRepository.persist(team);
        }
    }
}
