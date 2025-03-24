if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', fillInputFields);
} else {
    fillInputFields();
}

function fillInputFields() {
    document.addEventListener('DOMContentLoaded', () => {

        const team = JSON.parse(sessionStorage.getItem('teamToEdit'));
        const players = JSON.parse(sessionStorage.getItem('players'));




        if (team) {
            console.log('team to edit:', team);
            console.log('players to edit:', players);
            const teamnameInput = document.getElementById('teamname') as HTMLSelectElement;
            teamnameInput.value = team.name;
            for (let i = 0; i < players.length; i++) {
                const playerInput = document.getElementById(`player${i + 1}`) as HTMLSelectElement;
                playerInput.value = players[i].name;
            }
        } else {
            console.error('No team data found in sessionStorage');
        }
    });

}


async function updateTeam() {
    try {
        const teamData = sessionStorage.getItem('teamToEdit');
        if (!teamData) {
            throw new Error('No team data found in sessionStorage');
        }

        const teamId = JSON.parse(teamData).teamId;
        console.log('Team ID:', teamId);

        const teamName = (document.getElementById('teamname') as HTMLInputElement).value;
        console.log('Team Name:', teamName);

        const response = await fetch(`http://localhost:8080/api/team/renameTeam?teamId=${teamId}&teamName=${teamName}`, {
            method: "POST"
        });

        const data = await response.json();
        console.log('Data:', data);

        window.location.href = 'teamOverview.html';
    } catch (error) {
        console.error('Error:', error);
    }
}

async function updatePlayer(playerNum: number) {
    try {
        const newPlayerName =
            (document.getElementById(`player${playerNum}`) as HTMLInputElement).value;
        const teamData = sessionStorage.getItem("teamToEdit");

        const oldPlayerName =
            JSON.parse(sessionStorage.getItem('players'))[playerNum - 1].name;

        if (!teamData) {
            console.error("No team data found in session storage");
            return;
        }

        const teamId = JSON.parse(teamData).teamId;


        const playerIdResponse = await fetch(`
            http://localhost:8080/api/players/getAllPlayersOfTeam?teamId=${teamId}`);

        let data = await playerIdResponse.json();


        for (let i = 0; i < data.length; i++) {
            if (i === playerNum - 1) {
                data = data[i].playerId;
                break;
            }
        }

        console.log('Data:', data);
        const playerId = data;
        console.log('Player ID:', playerId);


        const url =
            `http://localhost:8080/api/players/renamePlayer?playerId=${playerId}&teamId=${teamId}&playerName=${encodeURIComponent(newPlayerName)}`;
        const response = await fetch(url, {
            method: "POST"
        });
    } catch (error) {
        console.error("Error:", error);
    }
}


document.addEventListener('DOMContentLoaded', fillInputFields);