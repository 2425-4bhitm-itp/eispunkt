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


function displayTeam(teamElementId: string, team: { name: string; players: { name: string }[] }) {

    const teamLabel = document.getElementById(teamElementId) as HTMLElement;
    if (teamLabel) {
        teamLabel.textContent = `${team.name}`;
    }


    team.players.forEach((player, index) => {
        const playerElement = document.getElementById(`${teamElementId}_player${index + 1}`) as HTMLElement;
        if (playerElement) {
            playerElement.textContent = player.name;
        }
    });
}

async function sendTeam(teamId:number) {
    try {
        const response = await fetch(`http://localhost:8080/api/team/findTeamId?teamId=${sessionStorage.getItem(`selectedTeam${teamId}`)}`);
        const data = await response.json();

        window.location.href = `../pages/editTeam.html`;

        window.addEventListener('load', () => {
            if (data) {
                console.log(data);
                document.getElementById('teamname').textContent = data.name;
                for (let i = 0; i < data.players.length; i++) {
                    document.getElementById(`player${i + 1}`).textContent = data.players[i].name;
                }
            } else {
                console.error('No team data found in sessionStorage');
            }
        })


    } catch (error) {
        console.error('Error:', error);
    }
}

function updateTeam(teamId:number) {



    // window.location.href = `../pages/editTeam.html`;
    //
    // const data = JSON.parse(sessionStorage.getItem(`toEditTeam${teamId}`));
    //
    // console.log(data)
    // document.getElementById('teamname').textContent = data.name;
    // for (let i = 0; i < data.players.length; i++) {
    //     document.getElementById(`player${i + 1}`).textContent = data.players[i].name;
    // }
}

document.addEventListener('DOMContentLoaded', loadSelectedTeams);
