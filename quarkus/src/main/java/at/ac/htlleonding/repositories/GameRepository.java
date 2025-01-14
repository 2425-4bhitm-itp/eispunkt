package at.ac.htlleonding.repositories;

import at.ac.htlleonding.entities.Game;
import at.ac.htlleonding.entities.Stage;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class GameRepository implements PanacheRepository<Game> {
    @Inject
    TeamRepository teamRepository;


    public Game findById(long id) {
        return find("id", id).firstResult();
    }

    public Game createGameWithTeams(long team1Id, long team2Id) {
        Game game = new Game();

        game.teams.add(teamRepository.findById(team1Id));
        game.teams.add(teamRepository.findById(team2Id));

        return game;
    }
}
