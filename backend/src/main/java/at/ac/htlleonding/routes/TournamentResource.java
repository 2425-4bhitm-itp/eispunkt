package at.ac.htlleonding.routes;

import at.ac.htlleonding.entities.Tournament;
import at.ac.htlleonding.repositories.TournamentRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

@Path("api/groups")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TournamentResource {
    @Inject
    TournamentRepository tournamentRepository;

    @POST
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createGroup(Tournament tournament) {
        Response.ResponseBuilder response;
        if (tournament == null) {
            response = Response.status(Response.Status.BAD_REQUEST);
        } else {
            tournamentRepository.persistAndFlush(tournament);
            var location = UriBuilder.fromResource(TournamentResource.class)
                                     .path(String.valueOf(tournament.getTournamentId()))
                                     .build();
            response = Response.status(Response.Status.CREATED).location(location);
        }
        return response.build();
    }

    @GET
    @Path("/{id:[0-9]+}")
    public Response getGroups(@PathParam("id") long groupId) {
        if (groupId == 0) {
            return Response.ok(tournamentRepository.getAllGroups()).build();
        } else {
            return Response.ok(tournamentRepository.findById(groupId)).build();
        }
    }

    @GET
    @Path("/team/{id:[0-9]+}")
    public Response getAllTeams(@PathParam("id") long groupId) {
        if (groupId == 0) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.ok(tournamentRepository.getAllTeams(groupId)).build();
    }

    @GET
    @Path("/game/{id:[0-9]+}")
    public Response getAllGames(@PathParam("id") long groupId) {
        if (groupId == 0) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.ok(tournamentRepository.getAllGames(groupId)).build();
    }

    @POST
    @Path("{groupId:[0-9]+}/{teamId:[0-9]+}")
    public Response addTeam(@PathParam("groupId") long groupId, @PathParam("teamId") long teamId) {
        if (groupId == 0 || teamId == 0) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.ok(tournamentRepository.addTeam(groupId, teamId)).build();
    }

    @PUT
    @Transactional
    public Response updateGroup(Tournament tournament) {
        if (tournament == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        tournamentRepository.updateGroup(tournament);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id:[0-9]+}")
    @Transactional
    public Response deleteGroup(@PathParam("id") long groupId) {
        if (groupId == 0) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        tournamentRepository.deleteById(groupId);
        return Response.noContent().build();
    }


}
