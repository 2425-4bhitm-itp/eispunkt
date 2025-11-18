package at.ac.htlleonding.routes;

import at.ac.htlleonding.entities.Tournament;
import at.ac.htlleonding.repositories.TournamentRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

@Path("api/tournaments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TournamentResource {
    @Inject
    TournamentRepository tournamentRepository;

    @POST
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createTournament(Tournament tournament) {
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
    public Response getTournament(@PathParam("id") long tournamentId) {
        if (tournamentId == 0) {
            return Response.ok(tournamentRepository.getAllTournaments()).build();
        } else {
            return Response.ok(tournamentRepository.findById(tournamentId)).build();
        }
    }

    @GET
    @Path("/team/{id:[0-9]+}")
    public Response getAllTeams(@PathParam("id") long tournamentId) {
        if (tournamentId == 0) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.ok(tournamentRepository.getAllTeams(tournamentId)).build();
    }

    @GET
    @Path("/game/{id:[0-9]+}")
    public Response getAllGames(@PathParam("id") long tournamentId) {
        if (tournamentId == 0) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.ok(tournamentRepository.getAllGames(tournamentId)).build();
    }

    @POST
    @Path("{tournamentId:[0-9]+}/{teamId:[0-9]+}")
    public Response addTeam(@PathParam("tournamentId") long tournamentId, @PathParam("teamId") long teamId) {
        if (tournamentId == 0 || teamId == 0) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.ok(tournamentRepository.addTeam(tournamentId, teamId)).build();
    }

    @PUT
    @Transactional
    public Response updateTournament(Tournament tournament) {
        if (tournament == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        tournamentRepository.updateTournament(tournament);
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
