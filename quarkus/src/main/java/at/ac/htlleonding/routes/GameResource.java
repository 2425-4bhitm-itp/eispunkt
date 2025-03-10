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

import java.util.List;

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
    @Produces(MediaType.APPLICATION_JSON)
    public Response startGame(@QueryParam("team1Id") long team1Id, @QueryParam("team2Id") long team2Id) {
        Game newGame = gameRepository.createGameWithTeams(team1Id,team2Id);
        return Response.ok(newGame.gameId).build();
    }


    @Path("/getGameInfo")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getInfo(@QueryParam("gameId") long gameId){
        return Response.ok(gameRepository.findById(gameId)).build();
    }

    @Path("/getCurrentStage")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response currentStage(@QueryParam("gameId") long gameId) {
        Game game = gameRepository.findById(gameId);
        Stage stage = gameRepository.getCurrentStage(game);
        return Response.ok(stage.stageId).build();
    }

    @Path("/createGameplan")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createGameplan(List<Long> teamIds) {
        Game game = new Game();
        for (long teamId : teamIds) {
            game.teams.add(teamRepository.findById(teamId));
        }

        return Response.ok(game.generateGameplan()).build();
    }
}