package at.ac.htlleonding.repositories;

import at.ac.htlleonding.entities.Game;
import at.ac.htlleonding.entities.Tournament;
import at.ac.htlleonding.entities.Team;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class TournamentRepository implements PanacheRepository<Tournament> {

    private final TeamRepository teamRepository;

    @jakarta.inject.Inject
    public TournamentRepository(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public List<Tournament> getAllTournaments() {
        return listAll();
    }

    public Tournament findById(long id) {
        return find("id", id).firstResult();
    }

    public List<Team> getAllTeams(long tournamentId) {
        return findById(tournamentId).getTeams();
    }

    public boolean addTeam(long tournamentId, long teamId) {
        return findById(tournamentId).addTeam(teamRepository.findById(teamId));
    }

    public void deleteTournament(long id) {
        deleteById(id);
    }

    public List<Game> getAllGames(long tournamentId) {
        List<Game> games = findById(tournamentId).getGames();

        if (games == null) {
            findById(tournamentId).generateGames();
            games = findById(tournamentId).getGames();
        }
        return games;
    }

    public List<String> generateGames(long tournamentId) {
        return findById(tournamentId).generateGames();
    }

    public void updateTournament(Tournament tournament) {
        if (tournament != null) {
            update("name = ?1, teams = ?2 where groupId = ?3",
                   tournament.getName(),
                   tournament.getTeams(),
                   tournament.getTournamentId());
        }
    }
}
