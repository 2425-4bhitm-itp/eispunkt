select = document.getElementById("team-select")
console.log(`select t from tournament_team tt
         join team t on tt.team_id = t.team_id
         where tt.tournament_id = {tournamentId};`)
console.log("(GET) /api/tournament/{tournamnetid}/teams")

function deleteTeam(team) {
    const teamBox = document.getElementById("team" + team);
    teamBox.style.display = "none";

    var teamSelectString = `team${team}-option`
    document.getElementById(teamSelectString).hidden = false;

    console.log(`delete from TOURNAMENT_TEAM where TOURNAMENT_TOURNAMENTID = {tournamentId} and TEAMS_TEAMID = {teamId};`)
    console.log("(DELETE) /api/tournament/{tournamentId}/{teamId}")
}

function selectTeam(team) {
    const teamSelect = document.getElementById("team-select");

    team = parseInt(teamSelect.value);
    teamSelect.options[teamSelect.selectedIndex].hidden = true;
    teamSelect.selectedIndex = 0;

    console.log("Selected team:" + team)

    const teamDiv = document.getElementById("team" + team);
    teamDiv.style.display = "flex";

    console.log("(PUT) /api/tournament/{tournamentId}/{teamId}")
    console.log(`insert into TOURNAMENT_TEAM (tournament_tournamentid, teams_teamid) values ({tournamentId}, {teamId});`)
}