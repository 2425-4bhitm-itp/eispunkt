console.log("(GET) /api/tournament/{tournamentId}/results")
console.log(`SELECT
            g.gameid,
            sc.team_teamid AS team_id,
            SUM(sc.score) AS team_score
        FROM e_tournament_game tg
                 JOIN public.game g     ON g.gameid = tg.games_gameid
                 JOIN public.stage s    ON g.gameid = s.game_gameid
                 JOIN public.turn t     ON s.stageid = t.stage_stageid
                 JOIN public.score sc   ON t.turnid = sc.turn_turnid
        where tg.group_groupid = {tournamentId}
        GROUP BY g.gameid, sc.team_teamid;`)