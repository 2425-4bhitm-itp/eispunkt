package at.ac.htlleonding.routes;

import at.ac.htlleonding.entities.Score;
import at.ac.htlleonding.entities.Stage;
import at.ac.htlleonding.entities.Team;
import at.ac.htlleonding.entities.Turn;
import at.ac.htlleonding.repositories.*;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

@Path("/api/turns")
public class TurnResource {

    @Inject
    TurnRepository turnRepository;

    @Inject
    StageRepository stageRepository;


    @Path("/newTurn")
    @Transactional
    public Response newTurn(@QueryParam("stageId") long stageId) {
        Turn turn = new Turn(stageRepository.findById(stageId));
        turnRepository.persist(turn);
        return Response.ok(turn).build();
    }

    @Path("/getStage")
    public Response getStage(@QueryParam("turnId") long turnId) {
        Stage stage = turnRepository.getStage(turnRepository.findById(turnId));
        return Response.ok(stage).build();
    }

    @Path("/getAllTurns")
    public Response getAllTurns() {
        return Response.ok(turnRepository.getAllTurns()).build();
    }
}