async function listTeams() {
    try {
        const response = await fetch('http://localhost:8080/api/team/getAllTeams')

        if (!response.ok) {
            throw new Error('http fehler status: ${response.status}')
        }

        const teams = await response.text()
        console.log(teams)
        let teamsJSON = JSON.parse(teams);

        const dropdown_team1 = document.getElementById('firstTeam') as HTMLSelectElement;
        const dropdown_team2 = document.getElementById('secTeam') as HTMLSelectElement;

        dropdown_team1.innerHTML = '<option value="">Bitte wählen...</option>'
        dropdown_team2.innerHTML = '<option value="">Bitte wählen...</option>'

        teamsJSON.forEach((team: { teamId: number, name: string }) => {
            const option1 = document.createElement('option');
            option1.value = team.teamId.toString();
            option1.textContent = team.name;
            dropdown_team1.appendChild(option1);

            const option2 = document.createElement('option');
            option2.value = team.teamId.toString();
            option2.textContent = team.name;
            dropdown_team2.appendChild(option2);

            console.log(team.name);
        });

    } catch (error) {
        console.error('Error: ', error);
    }
}

listTeams();