package at.ac.htlleonding.repositories;

import at.ac.htlleonding.dto.MatchDto;
import at.ac.htlleonding.entities.Game;
import at.ac.htlleonding.entities.Team;
import at.ac.htlleonding.entities.Tournament;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.LocalDate;
import java.util.*;

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

    public List<Tournament> getTournamentsOfTeam(long teamId) {
        return find("SELECT t FROM Tournament t JOIN t.teams team WHERE team.teamId = ?1",
                    teamId).list();
    }

    public Team addTeam(long tournamentId, long teamId) {
        Tournament tournament = findById(tournamentId);
        Team team = teamRepository.findById(teamId);

        if (tournament != null && team != null && !tournament.getTeams().contains(team)) {
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
            update("name = ?1, location = ?2 where tournamentId = ?3", tournament.getName(),
                   tournament.getLocation(), tournament.getTournamentId());
        }
    }

    private List<MatchDto> generateGames(Tournament tournament) {
        // If games already exist, convert them to MatchDtos with bye information
        if (tournament.getGames() != null && !tournament.getGames().isEmpty()) {
            List<MatchDto> matches = new ArrayList<>();
            for (Game game : tournament.getGames()) {
                List<Team> teams = game.getTeams();
                if (teams.size() == 2) {
                    matches.add(new MatchDto(teams.get(0), teams.get(1), game.getGameId()));
                } else if (teams.size() == 1) {
                    matches.add(new MatchDto(teams.getFirst(), null, null));
                }
            }
            return matches;
        }

        List<MatchDto> gameplan = new LinkedList<>();
        List<Team> teams = new LinkedList<>(tournament.getTeams());

        boolean hasOddTeamSize = teams.size() % 2 != 0;
        if (hasOddTeamSize) {
            teams.add(null);
        }

        int numRounds = teams.size() - 1;

        for (int round = 0; round < numRounds; round++) {
            for (int i = 0; i < teams.size() / 2; i++) {
                Team team1 = teams.get(i);
                Team team2 = teams.get(teams.size() - 1 - i);

                if (team1 != null && team2 != null && team1 != team2) {
                    Game game = new Game();
                    game.setDate(LocalDate.now());
                    game.addTeam(team1);
                    game.addTeam(team2);

                    gameRepository.persist(game);

                    tournament.addGame(game);

                    gameplan.add(new MatchDto(team1, team2, game.getGameId()));
                } else {
                    Team playing = team1 == null ? team2 : team1;
                    gameplan.add(new MatchDto(playing, null, null));
                }
            }

            Team lastTeam = teams.removeLast();
            teams.add(1, lastTeam);
        }

        persistAndFlush(tournament);
        return gameplan;
    }


    public List<List<MatchDto>> generateGamesPaginated(Tournament tournament) {
        List<MatchDto> matches = new ArrayList<>(generateGames(tournament));
        List<List<MatchDto>> rounds = new ArrayList<>();
        List<Team> allTeams = tournament.getTeams();

        while (!matches.isEmpty()) {
            List<MatchDto> currentRound = new ArrayList<>();
            List<MatchDto> remainingMatches = new ArrayList<>();

            // Set to track teams that have already played in this round
            Set<Team> teamsInRound = new HashSet<>();

            for (MatchDto match : matches) {
                Team team1 = match.team1();
                Team team2 = match.team2();

                // Check if both teams (or the single team if other is null) are available
                boolean team1Available = !teamsInRound.contains(team1);
                boolean team2Available = team2 == null || !teamsInRound.contains(team2);

                if (team1Available && team2Available) {
                    currentRound.add(match);
                    teamsInRound.add(team1);
                    if (team2 != null) {
                        teamsInRound.add(team2);
                    }
                } else {
                    remainingMatches.add(match);
                }
            }

            if (!currentRound.isEmpty()) {
                // Add teams that are on a break (bye) to the round
                for (Team team : allTeams) {
                    if (!teamsInRound.contains(team)) {
                        currentRound.add(new MatchDto(team, null, null));
                    }
                }
                rounds.add(currentRound);
            }

            matches = remainingMatches;

            // Prevent infinite loop if we can't add any more matches
            if (!currentRound.isEmpty() && matches.isEmpty()) {
                break;
            } else if (currentRound.isEmpty() && !matches.isEmpty()) {
                // This shouldn't happen with valid tournament data
                rounds.add(matches);
                break;
            }
        }

        persistAndFlush(tournament);

        return rounds;
    }


}
