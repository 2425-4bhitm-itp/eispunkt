package at.ac.htlleonding.routes;

import at.ac.htlleonding.entities.Group;
import at.ac.htlleonding.repositories.GroupRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

@Path("api/groups")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GroupRessource {
    @Inject
    GroupRepository groupRepository;

    @POST
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createGroup(Group group) {
        Response.ResponseBuilder response;
        if (group == null) {
            response = Response.status(Response.Status.BAD_REQUEST);
        } else {
            groupRepository.persistAndFlush(group);
            var location = UriBuilder.fromResource(GroupRessource.class)
                                     .path(String.valueOf(group.getGroupId()))
                                     .build();
            response = Response.status(Response.Status.CREATED).location(location);
        }
        return response.build();
    }

    @GET
    @Path("/{id:[0-9]+}")
    public Response getGroups(@PathParam("id") long groupId) {
        if (groupId == 0) {
            return Response.ok(groupRepository.getAllGroups()).build();
        } else {
            return Response.ok(groupRepository.findById(groupId)).build();
        }
    }

    @GET
    @Path("/team/{id:[0-9]+}")
    public Response getAllTeams(@PathParam("id") long groupId) {
        if (groupId == 0) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.ok(groupRepository.getAllTeams(groupId)).build();
    }

    @GET
    @Path("/game/{id:[0-9]+}")
    public Response getAllGames(@PathParam("id") long groupId) {
        if (groupId == 0) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.ok(groupRepository.getAllGames(groupId)).build();
    }

    @POST
    @Path("{groupId:[0-9]+}/{teamId:[0-9]+}")
    public Response addTeam(@PathParam("groupId") long groupId, @PathParam("teamId") long teamId) {
        if (groupId == 0 || teamId == 0) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.ok(groupRepository.addTeam(groupId, teamId)).build();
    }

    @PUT
    @Transactional
    public Response updateGroup(Group group) {
        if (group == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        groupRepository.updateGroup(group);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id:[0-9]+}")
    @Transactional
    public Response deleteGroup(@PathParam("id") long groupId) {
        if (groupId == 0) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        groupRepository.deleteById(groupId);
        return Response.noContent().build();
    }


}
