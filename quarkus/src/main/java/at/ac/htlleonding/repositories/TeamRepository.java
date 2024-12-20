package at.ac.htlleonding.repositories;

import at.ac.htlleonding.entities.Team;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Singleton;

@Singleton
public class TeamRepository implements PanacheRepository<Team> {
}
