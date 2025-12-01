package at.ac.htlleonding.routes;

import at.ac.htlleonding.entities.Team;
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
        teamRepository.updateTeam(team);
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
}
