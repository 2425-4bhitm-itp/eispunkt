package at.ac.htlleonding.routes;

import at.ac.htlleonding.entities.Player;
import at.ac.htlleonding.repositories.PlayerRepository;

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

    @Path("/createPlayer")
    @GET
    @Transactional
    public Response createPlayer(@QueryParam("name") String name){
        if(name == null || name.isEmpty()){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }else{
            return Response.ok(playerRepository.createPlayer(name).playerId).build();
        }
    }


}
