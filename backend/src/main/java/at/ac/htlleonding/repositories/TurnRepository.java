package at.ac.htlleonding.repositories;

import at.ac.htlleonding.entities.Stage;
import at.ac.htlleonding.entities.Turn;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class TurnRepository implements PanacheRepository<Turn> {
    public Turn findById(long id) {
        return find("id", id).firstResult();
    }

    public Stage getStage(Turn turn) {
        return turn.getStage();
    }

    public List<Turn> getAllTurns() {
        return listAll();
    }

    public void updateTurn(Turn turn) {
        update("stage = ?1 where turnId = ?2", turn.getStage(), turn.getTurnId());
    }
}