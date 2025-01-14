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

    @Inject
    TeamRepository teamRepository;

    @Path("/createGame")
    @GET
    @Transactional
    public Response startGame(@QueryParam("team1Id") long team1Id, @QueryParam("team2Id") long team2Id) {
        Game newGame = new Game();
        Team team1 = teamRepository.findById(team1Id);
        Team team2 = teamRepository.findById(team2Id);
        newGame.teams.add(team1);
        newGame.teams.add(team2);
        gameRepository.persist(newGame);
        return Response.ok(newGame.gameId).build();
    }


    @Path("/getGameInfo")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getInfo(@QueryParam("gameId") int gameId){
        return Response.ok(gameRepository.findById((long) gameId)).build();
    }


}