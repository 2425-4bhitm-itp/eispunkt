package at.ac.htlleonding.repositories;

import at.ac.htlleonding.entities.Stage;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class StageRepository implements PanacheRepository<Stage> {
    public Stage findById(long id) {
        return find("id", id).firstResult();
    }
}
