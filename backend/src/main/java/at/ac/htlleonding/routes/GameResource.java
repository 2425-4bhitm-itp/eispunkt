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

    @GET
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createGame() {
        Game newGame = gameRepository.createGame();
        return Response.ok(newGame).build();
    }

    @Path("/{id:[0-9]+}")
    @GET
    public Response findGameById(@PathParam("id") long gameId) {
        if (gameId == 0) {
            return Response.ok(gameRepository.getAllGames()).build();
        }
        return Response.ok(gameRepository.findById(gameId)).build();
    }

    @Path("/{gameId:[0-9]+}/{teamId:[0-9]+}")
    @POST
    @Transactional
    public Response addTeamToGame(@PathParam("gameId") long gameId, @PathParam("teamId") long teamId) {
        return Response.ok(gameRepository.addTeamToGame(gameId, teamId)).build();
    }

    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateGame(Game game) {
        if (game == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        gameRepository.updateGame(game);
        return Response.ok().build();
    }

    @Path("/{id:[0-9]+}")
    @DELETE
    @Transactional
    public Response deleteGame(@PathParam("id") long gameId) {
        Game game = gameRepository.findById(gameId);
        if (game == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        gameRepository.delete(game);
        return Response.noContent().build();
    }
}