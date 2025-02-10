package at.ac.htlleonding.routes;

import at.ac.htlleonding.repositories.GameRepository;
import at.ac.htlleonding.repositories.GameTeamRepository;
import at.ac.htlleonding.repositories.TeamRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

@Path("api/gameteams")
public class GameTeamResource {
    @Inject
    GameTeamRepository gameTeamRepository;
    @Inject
    TeamRepository teamRepository;
    @Inject
    GameRepository gameRepository;

    @Path("/getAllGameTeams")
    @GET
    public Response getAllGameTeams() {
        return Response.ok(gameTeamRepository.getAllGameTeams()).build();
    }

    @Path("/getGamesOfTeam")
    @GET
    public Response getGamesOfTeam(@QueryParam("teamId") long teamId) {
        return Response.ok(gameTeamRepository.getGamesOfTeam(teamRepository.findById(teamId))).build();
    }

    @Path("/getTeamsOfGame")
    @GET
    public Response getTeamsOfGame(@QueryParam("gameId") long gameId) {
        return Response.ok(gameTeamRepository.getTeamsOfGame(gameRepository.findById(gameId))).build();
    }

    @Path("/addTeamToGame")
    @GET
    public Response addTeamToGame(@QueryParam("gameId") long gameId, @QueryParam("teamId") long teamId) {
        return Response.ok(gameTeamRepository.addTeamToGame(
                                                            gameRepository.findById(gameId),
                                                            teamRepository.findById(teamId)
                                                           )).build();
    }
}
