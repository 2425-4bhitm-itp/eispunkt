# Frontend API & SQL 2025
- Sammlung der Routen und SQL Befehle von den jeweiligen Frontend-Seiten

## Startseite
- keine
---
## Teamseite
- SQL: `select * from TEAM;`
- API: (GET) `/api/team/0`

### Team-Erstellen
- SQL: `insert into TEAM (name) values ('{name}');`
- API: (POST) `/api/team/create/{teamName}`

### Team-Löschen
- SQL: `delete from TEAM where TEAMID = {teamId};`
- API: (DELETE) `/api/team/{teamId}`

### Team-Spieler
- SQL: `select * from PLAYER where TEAM_TEAMID = {teamId} order by PLAYER_ID;`
- API: (GET) `/api/players/team/{teamId}`

### Team-Spieler-Erstellen
- SQL: `insert into PLAYER (name, team_teamid) values ('{name}', {teamId});`
- API: (POST) `/api/players/{player}` - Player mit name und teamId im request body

### Team-Spieler-Bearbeiten
- SQL: `update PLAYER set NAME = '{name}' where PLAYERID = {playerId};`
- API: (PATCH) `/api/players/rename/{playerId}?newName={name}`

### Team-Spieler-Löschen
- SQL: `delete from PLAYER where PLAYERID = {playerId};`
- API: (DELETE) `/api/players/{playerId}`
---
## Turnierseite
- SQL: `select * from TOURNAMENT;`
- API: (GET) `/api/tournament/0`

### Turnier-Erstellen
- SQL: `insert into TOURNAMENT (name, loaction) values ('{name}', '{location}');`
- API: (POST) `/api/tournament/` - Turnier mit name und location im request body

### Turnier-Löschen
- SQL: `delete from TOURNAMENT where TOURNAMENTID = {tournamentId};`
- API: (DELETE) `/api/tournament/{tournamentId}`

### Turnier-Details
- SQL: `select t from tournament_team tt
         join team t on tt.team_id = t.team_id
         where tt.tournament_id = {tournamentId};`
- API: (GET) `/api/tournament/{tournamnetid}/teams`

### Turnier-Team-Auswählen
- SQL: `insert into TOURNAMENT_TEAM (tournament_tournamentid, teams_teamid) values ({tournamentId}, {teamId});`
- API: (PUT) `/api/tournament/{tournamentId}/{teamId}`

### Turnier-Team-Entfernen
- SQL: `delete from TOURNAMENT_TEAM where TOURNAMENT_TOURNAMENTID = {tournamentId} and TEAMS_TEAMID = {teamId};`
- API: (DELETE) `/api/tournament/{tournamentId}/{teamId}`

### Turnier-Games-Ergebnisse
- SQL: `SELECT
            g.gameid,
            sc.team_teamid AS team_id,
            SUM(sc.score) AS team_score
        FROM e_tournament_game tg
                 JOIN public.game g     ON g.gameid = tg.games_gameid
                 JOIN public.stage s    ON g.gameid = s.game_gameid
                 JOIN public.turn t     ON s.stageid = t.stage_stageid
                 JOIN public.score sc   ON t.turnid = sc.turn_turnid
        where tg.group_groupid = {tournamentId}
        GROUP BY g.gameid, sc.team_teamid;`
- API: (GET) `/api/tournament/{tournamentId}/results`


### Turnier-Games-Starten
- SQL: `select tg.GAMES_GAMEID from TOURNAMENT_GAME tg where tg.TOURNAMNET_TOURNAMENTID = {tournamentId} 
        and tg.GAME_GAMEID = {gameId};`
- API: (GET) `/api/tournament/{tournamentId}/{gameId}`

---
## Game
- SQL: `select t.teamid, t.name, s.stageid, tu.turnid, sc.scoreid, sc.score
        from GAME g
                 join public.game_team gt on g.gameid = gt.games_gameid
                 join public.team t on t.teamid = gt.teams_teamid
                 join public.stage s on g.gameid = s.game_gameid
                 join turn tu on s.stageid = tu.stage_stageid
                 join score sc on tu.turnid = sc.turn_turnid
        where g.gameid = 2
        group by t.teamid, s.stageid, tu.turnid, sc.scoreid;`
- API: (GET) `/api/game/{gameId}/summary`

### Game starten
- SQL: `insert into Stage (stagenumber, game_gameid) values ({stage.stagenumber}, {stage.gameId});`
- API: (POST) `/api/stage/{stage}` - Stage Objekt  im request body
- - SQL: `insert into Turn (stage_stageid) values ({turn.stage_stageid});`
- API: (POST) `/api/turn/{turn}` - Turn Objekt mit im request body
- - SQL: `insert into Score (score, team_teamid, turn_turnid) values ({score.score},
            {score.turn_turnid}, {score.game_gameId});`
- API: (POST) `/api/score/{score}` - Score Objekt im request body

### Game-Score-Erhöhen/Verringern
- SQL: `UPDATE public.score
        SET score = score.score,
            team_teamid = score.team,
            turn_turnid = score.turn
        WHERE scoreid = score.scoreId;`
- API: (PUT) `/api/scores/{score}` - Score Objekt mit neuem Punktestand im request body

---
## Ergebnis
- Alle Daten vorhanden