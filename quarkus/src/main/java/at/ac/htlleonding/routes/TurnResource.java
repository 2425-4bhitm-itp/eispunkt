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

    @Path("/newTurn")
    @Transactional
    public Response newTurn(@QueryParam("stage") Stage stage) {
        Turn turn = new Turn(stage);
        turnRepository.persist(turn);
        return Response.ok(turn).build();
    }

    @Path("/getStage")
    public Response getStage(@QueryParam("turn") Turn turn) {
        Stage stage = turnRepository.getStage(turn);
        return Response.ok(stage).build();
    }
}