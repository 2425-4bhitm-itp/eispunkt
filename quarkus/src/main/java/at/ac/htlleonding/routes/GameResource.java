package at.ac.htlleonding.routes;


import at.ac.htlleonding.entities.Game;
import at.ac.htlleonding.entities.Stage;
import at.ac.htlleonding.entities.Team;
import at.ac.htlleonding.repositories.GameRepository;
import at.ac.htlleonding.repositories.TeamRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("api/games")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GameResource {

    @Inject
    GameRepository gameRepository;

    @Path("/createGame")
    @GET
    @Transactional
    public Response createGame() {
        Game newGame = gameRepository.createGame();
        return Response.ok(newGame).build();
    }


    @Path("/getGameInfo")
    @GET
    public Response getInfo(@QueryParam("gameId") long gameId){
        return Response.ok(gameRepository.findById(gameId)).build();
    }


}