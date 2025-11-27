package at.ac.htlleonding.repositories;

import at.ac.htlleonding.entities.Game;
import at.ac.htlleonding.entities.Tournament;
import at.ac.htlleonding.entities.Team;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@ApplicationScoped
public class TournamentRepository implements PanacheRepository<Tournament> {

    private final TeamRepository teamRepository;

    @Inject
    GameRepository gameRepository;

    @Inject
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

    public Team addTeam(long tournamentId, long teamId) {
        Tournament tournament = findById(tournamentId);
        Team team = teamRepository.findById(teamId);

        if(tournament != null && team != null && !tournament.getTeams().contains(team)) {
            tournament.addTeam(team);

            persistAndFlush(tournament);

            return team;
        }

        return null;
    }

    public void deleteTournament(long id) {
        deleteById(id);
    }

    public List<Game> getAllGames(long tournamentId) {
        Tournament tournament = findById(tournamentId);
        List<Game> games = tournament.getGames();

        if (games == null || games.isEmpty()) {
            generateGames(tournament);

            tournament = findById(tournamentId);
        }

        games = tournament.getGames();
        return games;
    }

    // Only updates name and location
    public void updateTournament(Tournament tournament) {
        if (tournament != null) {
            update("name = ?1, location = ?2 where tournamentId = ?3",
                   tournament.getName(),
                   tournament.getLocation(),
                   tournament.getTournamentId());
        }
    }

    public List<String> generateGames(Tournament tournament) {
        List<String> gameplan = new LinkedList<>();
        List<Team> teams = new LinkedList<>(tournament.getTeams());


        boolean hasOddTeamSize = (teams.size() % 2 != 0);
        if (hasOddTeamSize) {
            teams.add(null);
        }

        int numRounds = teams.size() - 1;

        for (int round = 0; round < numRounds; round++) {
            for (int i = 0; i < teams.size() / 2; i++) {
                Team team1 = teams.get(i);
                Team team2 = teams.get(teams.size() - 1 - i);

                if (team1 != null && team2 != null) {
                    Game game = new Game();
                    game.setDate(LocalDate.now());
                    game.addTeam(team1);
                    game.addTeam(team2);

                    if(!tournament.getGames().contains(game)) {
                        gameRepository.persist(game);

                        tournament.addGame(game);
                    }

                    gameplan.add(team1.getTeamId() + " vs " + team2.getTeamId());
                } else {
                    Team playing = team1 == null ? team2 : team1;
                    gameplan.add(playing.getTeamId() + " gets a break!");
                }
            }
            Team lastTeam = teams.removeLast();

            teams.add(1, lastTeam);
        }
        gameplan.sort(String::compareToIgnoreCase);


        persistAndFlush(tournament);

        return gameplan;
    }
}
