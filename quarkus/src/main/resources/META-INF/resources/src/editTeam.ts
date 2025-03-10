if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', fillInputFields);
} else {
    fillInputFields();
}

function fillInputFields() {
    document.addEventListener('DOMContentLoaded', () => {

        const data = JSON.parse(sessionStorage.getItem('teamToEdit'));
        if (data) {
            console.log('team to edit:', data);
            const teamnameInput = document.getElementById('teamname') as HTMLSelectElement;
            teamnameInput.value = data.name;
            for (let i = 0; i < data.players.length; i++) {
                const playerInput = document.getElementById(`player${i + 1}`) as HTMLSelectElement;
                playerInput.value = data.players[i].name;
            }
        } else {
            console.error('No team data found in sessionStorage');
        }
    });

}


async function updateTeam() {
    try {
        const teamId = JSON.parse(sessionStorage.getItem('teamToEdit')).teamId;
        const teamName = (document.getElementById('teamname') as HTMLInputElement).value;
        const response = await fetch(`http://localhost:8080/api/team/updateTeam?teamId=${teamId}&teamName=${teamName}`, {
            method: "PUT"
        });
        console.log(await response.text());
    } catch (error) {
        console.error('Error:', error);
    }
}

async function updatePlayer(playerId: number) {
    try {
        const playerName = (document.getElementById(`player${playerId}`) as HTMLInputElement).value;
        const teamData = sessionStorage.getItem("teamToEdit");

        if (!teamData) {
            console.error("No team data found in session storage");
            return;
        }

        const teamId = JSON.parse(teamData).teamId;

        const url = `http://localhost:8080/api/players/updatePlayer?playerId=${playerId}&teamId=${teamId}&playerName=${encodeURIComponent(playerName)}`;

        const response = await fetch(url, {
            method: "PUT"
        });

        console.log(await response.text());
    } catch (error) {
        console.error("Error:", error);
    }
}


document.addEventListener('DOMContentLoaded', fillInputFields);