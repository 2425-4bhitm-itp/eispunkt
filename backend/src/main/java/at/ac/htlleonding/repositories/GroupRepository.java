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
        List<Game> games = findById(groupId).getGames();

        if (games == null) {
            findById(groupId).generateGames();
            games = findById(groupId).getGames();
        }
        return games;
    }

    public List<String> generateGames(long groupId) {
        return findById(groupId).generateGames();
    }

    public void updateGroup(Group group) {
        if (group != null) {
            update("name = ?1, teams = ?2 where groupId = ?3", group.getName(), group.getTeams(), group.getGroupId());
        }
    }
}
