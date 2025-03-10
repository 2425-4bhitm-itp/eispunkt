package at.ac.htlleonding.routes;

import at.ac.htlleonding.entities.Player;
import at.ac.htlleonding.repositories.PlayerRepository;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
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

    @Path("/updatePlayer")
    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    @Transactional
    public Response updatePlayer(
            @QueryParam("playerId") long playerId,
            @QueryParam("teamId") long teamId,
            @QueryParam("playerName") String name)
    {
        if (name == null || name.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else {
            return Response.ok(playerRepository.update(
                        """
                        update Player p
                        set p.name = ?2
                        where p.playerId = ?1
                        """
                    ,playerId , name)).build();
        }
    }

}
