package at.ac.htlleonding.routes;


import at.ac.htlleonding.entities.Game;
import at.ac.htlleonding.repositories.GameRepository;
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

    @Path("/findGameById")
    @GET
    public Response findGameById(@QueryParam("gameId") long gameId) {
        return Response.ok(gameRepository.findById(gameId)).build();
    }

    @Path("/createGame")
    @GET
    @Transactional
    public Response createGame() {
        Game newGame = gameRepository.createGame();
        return Response.ok(newGame).build();
    }


    @Path("/getGameInfo")
    @GET
    public Response getInfo(@QueryParam("gameId") long gameId) {
        return Response.ok(gameRepository.findById(gameId)).build();
    }

    @Path("/getAllTeamsOfGame")
    @GET
    public Response getAllTeamsOfGame(@QueryParam("gameId") long gameId) {
        return Response.ok(gameRepository.getAllTeamsOfGame(gameId)).build();
    }

    @Path("/getAllGames")
    @GET
    public Response getAllGames() {
        return Response.ok(gameRepository.getAllGames()).build();
    }

    @Path("/addTeamToGame")
    @POST
    @Transactional
    public Response addTeamToGame(@QueryParam("gameId") long gameId, @QueryParam("teamId") long teamId) {
        return Response.ok(gameRepository.addTeamToGame(gameId, teamId)).build();
    }

}