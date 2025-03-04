package at.ac.htlleonding.repositories;

import at.ac.htlleonding.entities.Team;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.List;

@Singleton
public class TeamRepository implements PanacheRepository<Team> {
    @Inject
    PlayerRepository playerRepository;

    public Team findById(long id) {
        return find("id", id).firstResult();
    }

    public Team createTeam(String name) {
        if (!(name == null || name.isBlank())) {
            Team team = new Team();
            team.name = name;

            persist(team);

            return team;
        }
        return null;
    }

    public Team createTeamWithPlayers(String name, long... playerIds) {
        if (!(name == null || name.isBlank())) {
            Team team = new Team();
            team.name = name;

            for (long playerId : playerIds) {
                team.players.add(playerRepository.findById(playerId));
            }

            persist(team);

            return team;
        }
        return null;
    }

    public Team findByName(String name) {
        return find("name", name).firstResult();
    }

    public List<Team> getAllTeams() {
        return listAll();
    }

    public void addPlayerToTeam(long teamId, long playerId) {
        Team team = findById(teamId);
        team.players.add(playerRepository.findById(playerId));
    }
}
