package at.ac.htlleonding.repositories;

import at.ac.htlleonding.entities.Score;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ScoreRepository implements PanacheRepository<Score>{
    public Score findById(long id) {
        return find("id", id).firstResult();
    }
}
