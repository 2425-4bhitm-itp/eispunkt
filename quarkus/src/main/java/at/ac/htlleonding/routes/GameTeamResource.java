package at.ac.htlleonding.routes;

import at.ac.htlleonding.entities.Game;
import at.ac.htlleonding.entities.Team;
import at.ac.htlleonding.repositories.GameTeamRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("api/gameteams")
public class GameTeamResource {
    @Inject
    GameTeamRepository gameTeamRepository;

    @Path("/getAllGameTeams")
    @GET
    public Response getAllGameTeams() {
        return Response.ok(gameTeamRepository.getAllGameTeams()).build();
    }

    @Path("/getGamesOfTeam")
    @GET
    public Response getGamesOfTeam(Team team) {
        return Response.ok(gameTeamRepository.getGamesOfTeam(team)).build();
    }

    @Path("/getTeamsOfGame")
    @GET
    public Response getTeamsOfGame(Game game) {
        return Response.ok(gameTeamRepository.getTeamsOfGame(game)).build();
    }

    @Path("/addTeamToGame")
    @GET
    public Response addTeamToGame(Game game, Team team) {
        return Response.ok(gameTeamRepository.addTeamToGame(game, team)).build();
    }
}
