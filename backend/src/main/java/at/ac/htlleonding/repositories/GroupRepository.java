package at.ac.htlleonding.repositories;

import at.ac.htlleonding.entities.Game;
import at.ac.htlleonding.entities.Group;
import at.ac.htlleonding.entities.Team;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class GroupRepository implements PanacheRepository<Group> {

    private final TeamRepository teamRepository;

    @jakarta.inject.Inject
    public GroupRepository(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public List<Group> getAllGroups() {
        return listAll();
    }

    public Group findById(long id) {
        return find("id", id).firstResult();
    }

    public List<Team> getAllTeams(long groupId) {
        return findById(groupId).getTeams();
    }

    public boolean addTeam(long groupId, long teamId) {
        return findById(groupId).addTeam(teamRepository.findById(teamId));
    }

    public void deleteGroup(long id) {
        delete("id", id);
    }

    public List<Game> getAllGames(long groupId) {
        return findById(groupId).getGames();
    }
}
