var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
function listTeams() {
    return __awaiter(this, void 0, void 0, function* () {
        try {
            const response = yield fetch('http://localhost:8080/api/team/getAllTeams');
            if (!response.ok) {
                throw new Error('http fehler status: ${response.status}');
            }
            const teams = yield response.text();
            console.log(teams);
            let teamsJSON = JSON.parse(teams);
            const dropdown_team1 = document.getElementById('firstTeam');
            const dropdown_team2 = document.getElementById('secTeam');
            dropdown_team1.innerHTML = '<option disabled value="">Bitte wählen...</option>';
            dropdown_team2.innerHTML = '<option disabled value="">Bitte wählen...</option>';
            teamsJSON.forEach((team) => {
                const option1 = document.createElement('option');
                option1.value = team.teamId.toString();
                option1.textContent = team.name;
                dropdown_team1.appendChild(option1);
                const option2 = document.createElement('option');
                option2.value = team.teamId.toString();
                option2.textContent = team.name;
                dropdown_team2.appendChild(option2);
                console.log(team.name);
                dropdown_team1.addEventListener('change', () => {
                    const selectedTeam1 = dropdown_team1.value;
                    sessionStorage.setItem('selectedTeam1', selectedTeam1);
                    console.log(`Ausgewähltes Team 1: ${selectedTeam1}`);
                });
                dropdown_team2.addEventListener('change', () => {
                    const selectedTeam2 = dropdown_team2.value;
                    sessionStorage.setItem('selectedTeam2', selectedTeam2);
                    console.log(`Ausgewähltes Team 2: ${selectedTeam2}`);
                });
            });
        }
        catch (error) {
            console.error('Error: ', error);
        }
    });
}
listTeams();
//# sourceMappingURL=loadTeams.js.map