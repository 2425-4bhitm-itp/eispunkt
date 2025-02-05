package at.ac.htlleonding.repositories;

import at.ac.htlleonding.entities.Game;
import at.ac.htlleonding.entities.GameTeam;
import at.ac.htlleonding.entities.Team;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class GameTeamRepository implements PanacheRepository<GameTeam> {
    public GameTeam findById(long id) {
        return find("id", id).firstResult();
    }

    public List<GameTeam> getAllGameTeams() {
        return listAll();
    }

    public List<Game> getGamesOfTeam(Team team) {
        return find("#GameTeam.getGamesOfTeam", team).stream()
                                                     .map(GameTeam::getGame)
                                                     .collect(Collectors.toList());
    }

    public List<Team> getTeamsOfGame(Game game) {
        return find("#GameTeam.getTeamsOfGame", game).stream()
                                                     .map(GameTeam::getTeam)
                                                     .collect(Collectors.toList());

    }

    public GameTeam addTeamToGame(Game game, Team team) {
        GameTeam gameTeam = new GameTeam(game, team);
        persist(gameTeam);
        return gameTeam;
    }
}
