select = document.getElementById("team-select")
console.log(`select t from tournament_team tt
         join team t on tt.team_id = t.team_id
         where tt.tournament_id = {tournamentId};`)
console.log("(GET) /api/tournament/{tournamnetid}/teams")

function deleteTeam(team) {
    document.getElementById(team).remove();
    console.log(`delete from TOURNAMENT_TEAM where TOURNAMENT_TOURNAMENTID = {tournamentId} and TEAMS_TEAMID = {teamId};`)
    console.log("(DELETE) /api/tournament/{tournamentId}/{teamId}")
}

function selectTeam() {
    console.log("(PUT) /api/tournament/{tournamentId}/{teamId}")
    console.log(`insert into TOURNAMENT_TEAM (tournament_tournamentid, teams_teamid) values ({tournamentId}, {teamId});`)
}