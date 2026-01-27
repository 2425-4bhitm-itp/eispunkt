package at.ac.htlleonding.repositories;

import at.ac.htlleonding.entities.Team;
import at.ac.htlleonding.routes.TeamResource;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class TeamRepository implements PanacheRepository<Team> {
    @Inject
    TeamResource teamResource;

    public Team findById(long teamId) {
        return find("id", teamId).firstResult();
    }

    public Team findByName(String name) {
        return find("name", name).firstResult();
    }

    public List<Team> getAllTeams() {
        System.out.println("Getting all teams!!!");

        return listAll(Sort.ascending("name"));
    }

    public void renameTeam(long teamId, String newName) {
        update("name = ?1 where teamId = ?2", newName, teamId);
    }

    public void updateTeam(Team team) {
        update("name = ?1, games = ?2 where teamId = ?3", team.getName(), team.getGames(), team.getTeamId());
    }

    @Inject TournamentRepository tournamentRepository;

    @Inject PlayerRepository playerRepository;

    public void deleteTeam(long teamId) {
        Team team = find("teamId", teamId).firstResult();

        // Delete players belonging to the team
        playerRepository.update("team = null where team = ?1", team);

        // Finally delete the team
        delete(team);
    }

}
