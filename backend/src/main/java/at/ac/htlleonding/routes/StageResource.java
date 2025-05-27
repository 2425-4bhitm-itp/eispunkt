package at.ac.htlleonding.routes;

import at.ac.htlleonding.entities.Stage;
import at.ac.htlleonding.repositories.StageRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

@Path("api/stages")
public class StageResource {
    @Inject
    StageRepository stageRepository;

    @Path("/{id:[0-9]+}")
    @GET
    public Response getStageById(@PathParam("id") long stageId) {
        if (stageId == 0) {
            return Response.ok(stageRepository.findAll()).build();
        } else {
            return Response.ok(stageRepository.findById(stageId)).build();
        }
    }

    @POST
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createStage(Stage stage) {
        Response.ResponseBuilder response;
        if (stage == null) {
            response = Response.status(Response.Status.BAD_REQUEST);
        } else {
            stageRepository.persistAndFlush(stage);
            var location = UriBuilder.fromResource(ScoreResource.class)
                                     .path(String.valueOf(stage.getStageId()))
                                     .build();
            response = Response.status(Response.Status.CREATED).location(location);
        }
        return response.build();
    }

    @PUT
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateStage(Stage stage){
        if(stage == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        stageRepository.updateStage(stage);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id:[0-9]+}")
    @Transactional
    public Response deleteGroup(@PathParam("id") long stageId) {
        if (stageId == 0) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        stageRepository.deleteById(stageId);
        return Response.noContent().build();
    }
}
