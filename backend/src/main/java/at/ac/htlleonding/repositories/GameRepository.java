package at.ac.htlleonding.repositories;

import at.ac.htlleonding.entities.Game;
import at.ac.htlleonding.entities.Team;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class GameRepository implements PanacheRepository<Game> {
    @Inject
    GameRepository gameRepository;

    public Game findById(long id) {
        return find("id", id).firstResult();
    }

    public Game createGame() {
        Game newGame = new Game();
        persist(newGame);
        return newGame;
    }


    public List<Game> getAllGames() {
        return listAll();
    }

    public List<Team> getAllTeamsOfGame(long gameId) {
        return gameRepository.findById(gameId).getTeams();
    }

    public boolean addTeamToGame(long gameId, long teamId) {
        Game game = gameRepository.findById(gameId);
        Team team = gameRepository.getEntityManager().find(Team.class, teamId);
        return game.addTeam(team);
    }

    public void updateGame(Game game) {
        update("date = ?1, teams = ?2 where id = ?3", game.getDate(), game.getTeams(), game.getGameId());
    }
}
