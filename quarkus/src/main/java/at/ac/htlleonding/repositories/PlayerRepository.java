package at.ac.htlleonding.repositories;

import at.ac.htlleonding.entities.Player;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PlayerRepository implements PanacheRepository<Player> {
    public Player findById(long id) {
        return find("id", id).firstResult();
    }

    public Player createPlayer(String name) {
        if (!(name == null || name.isBlank())) {
            Player player = new Player();
            player.name = name;

            persist(player);

            return player;
        }
        return null;
    }
}
