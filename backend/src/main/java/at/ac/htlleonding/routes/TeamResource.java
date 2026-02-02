package at.ac.htlleonding.routes;

import at.ac.htlleonding.entities.Player;
import at.ac.htlleonding.entities.Team;
import at.ac.htlleonding.repositories.PlayerRepository;
import at.ac.htlleonding.repositories.TeamRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

@Path("/api/teams")
public class TeamResource {
    @Inject
    TeamRepository teamRepository;
    @Inject
    PlayerRepository playerRepository;

    @POST
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createTeam(@QueryParam("teamName") String teamName) {
        Response.ResponseBuilder response;
        if (teamName == null) {
            response = Response.status(Response.Status.BAD_REQUEST);
        } else {
            teamRepository.persistAndFlush(new Team(teamName));
            var location = UriBuilder.fromResource(TeamResource.class)
                    .path(String.valueOf(teamRepository.findByName(teamName).getTeamId())).build();
            response = Response.status(Response.Status.CREATED).location(location);
        }
        return response.build();
    }


    @Path("/{id:[0-9]+}")
    @GET
    public Response findTeamById(@PathParam("id") long teamId) {
        if (teamId == 0) {
            return Response.ok(teamRepository.getAllTeams()).build();
        }
        return Response.ok(teamRepository.findById(teamId)).build();
    }

    @Path("/{teamName}")
    @GET
    public Response findTeamByName(@PathParam("teamName") String teamName) {
        return Response.ok(teamRepository.findByName(teamName)).build();
    }

    @PUT
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateTeam(Team team) {
        if (team == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        teamRepository.persist(team);
        return Response.ok().build();
    }

    @Path("{id:[0-9]+}/{newName}")
    @PATCH
    @Transactional
    public Response renameTeam(@PathParam("id") long teamId, @PathParam("newName") String newName) {
        teamRepository.renameTeam(teamId, newName);
        return Response.noContent().build();
    }

    @Path("/{id:[0-9]+}")
    @DELETE
    @Transactional
    public Response deleteTeam(@PathParam("id") long teamId) {
        teamRepository.deleteTeam(teamId);
        return Response.noContent().build();
    }

    @GET
    @Path("isVisible/{id:[0-9]+}")
    @Transactional
    public Response flipIsVisible(@PathParam("id") long teamId) {
        Response response = Response.status(Response.Status.BAD_REQUEST).build();

        Team team = teamRepository.findById(teamId);

        if (team != null) {
            team.setVisible(!team.isVisible());
            teamRepository.persistAndFlush(team);
            response = Response.ok(team.isVisible()).build();
        }

        return response;
    }

    @PUT
    @Path("{teamId:[0-9]+}/addPlayer/{playerId:[0-9]+}")
    @Transactional
    public Response addPlayerToTeam(@PathParam("teamId") long teamId,
                                    @PathParam("playerId") long playerId) {

        Team team = teamRepository.findById(teamId);
        Player player = playerRepository.findById(playerId);

        if (team == null || player == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        if (player.getTeam() != null) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity(String.format(
                                   "Player #%d already belongs to Team #%d",
                                   playerId,
                                   player.getTeam().getTeamId()))
                           .build();
        }

        teamRepository.addPlayerToTeam(team, player);

        return Response.ok(String.format(
                "Player #%d added to Team #%d",
                playerId,
                teamId)).build();
    }

    @PUT
    @Path("{teamId:[0-9]+}/removePlayer/{playerId:[0-9]+}")
    @Transactional
    public Response removePlayerFromTeam(@PathParam("teamId") long teamId,
                                    @PathParam("playerId") long playerId) {
        Team team = teamRepository.findById(teamId);
        Player player = playerRepository.findById(playerId);

        if (team == null || player == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        if (player.getTeam() == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity(String.format(
                                   "Player #%d does not belong to Team #%d",
                                   playerId,
                                   teamId))
                           .build();
        } else {
            teamRepository.removePlayerFromTeam(team, player);
            return Response.ok(String.format(
                    "Player #%d removed from Team #%d",
                    playerId,
                    teamId)).build();
        }
    }

}
