package at.ac.htlleonding.repositories;

import at.ac.htlleonding.entities.Game;
import at.ac.htlleonding.entities.Stage;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class StageRepository implements PanacheRepository<Stage> {
    public Stage findById(long id) {
        return find("id", id).firstResult();
    }

    public Game getGame(Stage stage) {
        return stage.getGame();
    }

    public List<Stage> getAllStages() {
        return listAll();
    }

    public Stage createStage(Game game, int stageNumber) {
        Stage stage = new Stage(game, stageNumber);
        persist(stage);
        return stage;
    }

    public void updateStage(Stage stage) {
        update("game = ?1, stageNumber = ?2 where stageId = ?3",
                stage.getGame(),
                stage.getStageNumber(),
                stage.getStageId());
    }
}
