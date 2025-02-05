package at.ac.htlleonding.repositories;

import at.ac.htlleonding.entities.Game;
import at.ac.htlleonding.entities.Stage;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class GameRepository implements PanacheRepository<Game> {
    @Inject
    GameRepository gameRepository;

    public Game findById(long id) {
        return find("id", id).firstResult();
    }

    public Game createGame() {
        Game newGame = new Game();
        persist(newGame);
        return newGame;
    }
}
