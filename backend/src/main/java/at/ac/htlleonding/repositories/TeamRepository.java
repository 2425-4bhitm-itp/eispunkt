package at.ac.htlleonding.repositories;

import at.ac.htlleonding.entities.Team;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.inject.Singleton;

import java.util.List;

@Singleton
public class TeamRepository implements PanacheRepository<Team> {
    public Team findById(long teamId) {
        return find("id", teamId).firstResult();
    }

    public Team findByName(String name) {
        return find("name", name).firstResult();
    }

    public List<Team> getAllTeams() {
        return listAll();
    }

    public void renameTeam(long teamId, String newName) {
        update("name = ?1 where teamId = ?2", newName, teamId);
    }

    public void updateTeam(Team team) {
        update("name = ?1, games = ?2 where teamId = ?3", team.getName(), team.getGames(), team.getTeamId());
    }
}
