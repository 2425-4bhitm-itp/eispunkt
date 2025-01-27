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

    @Inject
    TeamRepository teamRepository;

    @Inject
    GameRepository gameRepository;

    @Inject
    ScoreRepository scoreRepository;

    @Path("/newTurn")
    @Transactional
    public Response newTurn(@QueryParam("stageId") long stageId) {
        Stage stage = stageRepository.findById(stageId);
        Turn newTurn = new Turn();
        turnRepository.persist(newTurn);
        stage.turn.add(newTurn);
        return Response.ok(newTurn.turnId).build();
    }
}
