package at.ac.htlleonding.repositories;

import at.ac.htlleonding.dto.MatchDto;
import at.ac.htlleonding.entities.Game;
import at.ac.htlleonding.entities.Tournament;
import at.ac.htlleonding.entities.Team;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
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

    private List<MatchDto> generateGames(Tournament tournament) {
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

                    gameplan.add(
                            new MatchDto(team1, team2, game.getGameId())
                    );
                } else {
                    Team playing = team1 == null ? team2 : team1;
                    gameplan.add(
                            new MatchDto(playing, null, null)
                    );
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

        MatchDto match;
        rounds.add(new ArrayList<>());
        int failsCounter = 0;
        while (!matches.isEmpty()) {
            boolean roundIsFull = rounds.getLast().size() >= tournament.getTeams().size() / 2;

            match = matches.getFirst();

            if (rounds.isEmpty() || roundIsFull) {
                rounds.add(new ArrayList<>());
            }

            boolean team1InRound = checkTeamInRound(match.team1(), rounds.getLast());
            boolean team2InRound = checkTeamInRound(match.team2(), rounds.getLast());
            if(!team1InRound && !team2InRound) {
                rounds.getLast().add(match);
                matches.removeFirst();
                failsCounter = 0;
            } else if (failsCounter > matches.size()) {
                rounds.add(new ArrayList<>());
                failsCounter = 0;
            } else {
                failsCounter++;
                matches.removeFirst();
                matches.add(match);
            }
        }

        persistAndFlush(tournament);

        return rounds;
    }

    private boolean checkTeamInRound(Team team, List<MatchDto> currentRound) {
        boolean isInRound = false;

        if(team == null) {
            return isInRound;
        }

        for (MatchDto match : currentRound) {
            if (match.team2() == team || match.team1() == team) {
                isInRound = true;
                break;
            }
        }

        return isInRound;
    }
}
