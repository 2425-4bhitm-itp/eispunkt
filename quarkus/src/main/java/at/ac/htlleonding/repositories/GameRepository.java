package at.ac.htlleonding.repositories;

import at.ac.htlleonding.entities.Game;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Singleton;

@ApplicationScoped
public class GameRepository implements PanacheRepository<Game> {

}
