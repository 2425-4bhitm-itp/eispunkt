async function loadSelectedTeams() {

    const selectedTeam1Id = sessionStorage.getItem('selectedTeam1');
    const selectedTeam2Id = sessionStorage.getItem('selectedTeam2');

    if (!selectedTeam1Id || !selectedTeam2Id) {
        console.error("Keine Teams ausgewählt!");
        return;
    }

    try {

        const response = await fetch('http://localhost:8080/api/team/getAllTeams');
        if (!response.ok) {
            throw new Error(`Fehler beim Laden der Teams: Status ${response.status}`);
        }


        const teams = await response.json();


        const team1 = teams.find((team: { teamId: number }) => team.teamId.toString() === selectedTeam1Id);
        const team2 = teams.find((team: { teamId: number }) => team.teamId.toString() === selectedTeam2Id);

        if (team1) {
            displayTeam('team1', team1);
        } else {
            console.error(`Team mit ID ${selectedTeam1Id} nicht gefunden`);
        }

        if (team2) {
            displayTeam('team2', team2);
        } else {
            console.error(`Team mit ID ${selectedTeam2Id} nicht gefunden`);
        }

    } catch (error) {
        console.error("Fehler beim Abrufen der Teams:", error);
    }
}


async function displayTeam(teamElementId: string, team: { name: string; teamId: number;}) {

    const teamLabel = document.getElementById(teamElementId) as HTMLElement;
    if (teamLabel) {
        teamLabel.textContent = `${team.name}`;
    }
    const response = await fetch(`http://localhost:8080/api/players/getAllPlayersOfTeam?teamId=${team.teamId}`);

    const players = await response.json();


    players.forEach((player: { name: string }, index: number) => {
        const playerElement = document.getElementById(`${teamElementId}_player${index + 1}`) as HTMLElement;
        if (playerElement) {
            playerElement.textContent = player.name;
            console.log('player:', player);
        }
    });
}



async function sendTeam(teamNumber: number) {
    try {

        const response = await fetch(`http://localhost:8080/api/team/findTeamById?teamId=${sessionStorage.getItem(`selectedTeam${teamNumber}`)}`);
        const data = await response.json();

        const response2 = await fetch('http://localhost:8080/api/players/getAllPlayersOfTeam?teamId=' + sessionStorage.getItem(`selectedTeam${teamNumber}`));
        const players = await response2.json();

        sessionStorage.setItem(`players`, JSON.stringify(players));
        if (data) {
            sessionStorage.setItem('teamToEdit', JSON.stringify(data));
            window.location.href = `../pages/editTeam.html`;
        } else {
            console.error('No team data found in sessionStorage');
        }

    } catch (error) {
        console.error('Error:', error);
    }
}

document.addEventListener('DOMContentLoaded', loadSelectedTeams);
