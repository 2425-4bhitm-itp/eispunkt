package at.ac.htlleonding.repositories;

import at.ac.htlleonding.entities.Player;
import at.ac.htlleonding.entities.Team;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class PlayerRepository implements PanacheRepository<Player> {
    public Player findById(long id) {
        return find("id", id).firstResult();
    }

    public Player setTeamOfPlayer(Player player, Team team) {
        player.setTeam(team);
        return player;
    }

    public List<Player> getAllPlayersWithTeam(long teamId) {
        return list("#Player.getAllPlayersWithTeam", Parameters.with("teamId", teamId));
    }

    public void renamePlayer(long playerId, String newName) {
        update("name = ?1 where playerId = ?2", newName, playerId);
    }

    public List<Player> getAllPlayers() {
        return listAll();
    }

    public void updatePlayer(Player player) {
        if (player != null) {
            update("name = ?1, team = ?2 where playerId = ?3", player.getName(), player.getTeam(), player.getPlayerId());
        }
    }
}
