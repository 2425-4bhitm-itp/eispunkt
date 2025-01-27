package at.ac.htlleonding.routes;

import at.ac.htlleonding.repositories.StageRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("api/stages")
public class StageResource {
    @Inject
    StageRepository stageRepository;

    @GET
    public Response getStages() {
        return Response.ok(stageRepository.findAll()).build();
    }

    @GET
    public Response getStageById(long id) {
        return Response.ok(stageRepository.findById(id)).build();
    }
}
