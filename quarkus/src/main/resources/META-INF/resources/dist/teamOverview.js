var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
function loadSelectedTeams() {
    return __awaiter(this, void 0, void 0, function* () {
        const selectedTeam1Id = sessionStorage.getItem('selectedTeam1');
        const selectedTeam2Id = sessionStorage.getItem('selectedTeam2');
        if (!selectedTeam1Id || !selectedTeam2Id) {
            console.error("Keine Teams ausgewählt!");
            return;
        }
        try {
            const response = yield fetch('http://localhost:8080/api/team/getAllTeams');
            if (!response.ok) {
                throw new Error(`Fehler beim Laden der Teams: Status ${response.status}`);
            }
            const teams = yield response.json();
            const team1 = teams.find((team) => team.teamId.toString() === selectedTeam1Id);
            const team2 = teams.find((team) => team.teamId.toString() === selectedTeam2Id);
            if (team1) {
                displayTeam('team1', team1);
            }
            else {
                console.error(`Team mit ID ${selectedTeam1Id} nicht gefunden`);
            }
            if (team2) {
                displayTeam('team2', team2);
            }
            else {
                console.error(`Team mit ID ${selectedTeam2Id} nicht gefunden`);
            }
        }
        catch (error) {
            console.error("Fehler beim Abrufen der Teams:", error);
        }
    });
}
function displayTeam(teamElementId, team) {
    const teamLabel = document.getElementById(teamElementId);
    if (teamLabel) {
        teamLabel.textContent = `${team.name}`;
    }
    team.players.forEach((player, index) => {
        const playerElement = document.getElementById(`${teamElementId}_player${index + 1}`);
        if (playerElement) {
            playerElement.textContent = player.name;
        }
    });
}
document.addEventListener('DOMContentLoaded', loadSelectedTeams);
//# sourceMappingURL=teamOverview.js.map