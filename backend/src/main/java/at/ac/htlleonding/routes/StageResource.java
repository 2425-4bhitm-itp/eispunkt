package at.ac.htlleonding.routes;

import at.ac.htlleonding.repositories.GameRepository;
import at.ac.htlleonding.repositories.StageRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("api/stages")
public class StageResource {
    @Inject
    StageRepository stageRepository;

    @Inject
    GameRepository gameRepository;


    @GET
    public Response getAllStages() {
        return Response.ok(stageRepository.getAllStages()).build();
    }


    @Path("/{id:[0-9]+}")
    @GET
    public Response getStageById(@PathParam("id") long stageId) {
        return Response.ok(stageRepository.findById(stageId)).build();
    }

    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createStage(@QueryParam("gameId") long gameId, @QueryParam("stageNumber") int stageNumber) {
        return Response.ok(stageRepository.createStage(gameRepository.findById(gameId), stageNumber)).build();
    }

    @Path("/getGame")
    @GET
    public Response getGame(@QueryParam("stageId") long stageId) {
        return Response.ok(stageRepository.getGame(stageRepository.findById(stageId))).build();
    }
}
