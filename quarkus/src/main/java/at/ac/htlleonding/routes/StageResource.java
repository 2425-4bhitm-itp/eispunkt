package at.ac.htlleonding.routes;

import at.ac.htlleonding.repositories.GameRepository;
import at.ac.htlleonding.repositories.StageRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

@Path("api/stages")
public class StageResource {
    @Inject
    StageRepository stageRepository;

    @Inject
    GameRepository gameRepository;

    @Path("/getStages")
    @GET
    public Response getStages() {
        return Response.ok(stageRepository.findAll()).build();
    }

    @Path("/getStageById")
    @GET
    public Response getStageById(long id) {
        return Response.ok(stageRepository.findById(id)).build();
    }

    @Path("/createStage")
    @POST
    @Transactional
    public Response createStage(@QueryParam("gameId") long gameId, @QueryParam("stageNumber") int stageNumber) {
        return Response.ok(stageRepository.createStage(gameRepository.findById(gameId), stageNumber)).build();
    }

    @Path("/getGame")
    @GET
    public Response getGame(
            @QueryParam("stageId")  long stageId
                           ) {
        return Response.ok(stageRepository.getGame(stageRepository.findById(stageId))).build();
    }

    @Path("/getAllStages")
    @GET
    public Response getAllStages() {
        return Response.ok(stageRepository.getAllStages()).build();
    }

}
