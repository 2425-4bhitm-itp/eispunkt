package at.ac.htlleonding.repositories;

import at.ac.htlleonding.entities.Team;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.List;

@Singleton
public class TeamRepository implements PanacheRepository<Team> {
    @Inject

    public Team findById(long id) {
        return find("id", id).firstResult();
    }

    public Team createTeam(String name) {
        Team team = new Team(name);

        persist(team);

        return team;

    }

    public Team findByName(String name) {
        return find("name", name).firstResult();
    }

    public List<Team> getAllTeams() {
        return listAll();
    }
}
