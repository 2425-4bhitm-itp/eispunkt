package at.ac.htlleonding.routes;

import at.ac.htlleonding.entities.Game;
import at.ac.htlleonding.entities.Stage;
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

    @GET
    public Response getStages() {
        return Response.ok(stageRepository.findAll()).build();
    }

    @GET
    public Response getStageById(long id) {
        return Response.ok(stageRepository.findById(id)).build();
    }

    @POST
    @Transactional
    public Response createStage(@QueryParam("game") Game game) {
        return Response.ok(stageRepository.createStage(game)).build();
    }

    @GET
    public Response getGame(@QueryParam("stage") Stage stage) {
        return Response.ok(stageRepository.getGame(stage)).build();
    }

    @GET
    public Response getAllStages() {
        return Response.ok(stageRepository.getAllStages()).build();
    }

}
