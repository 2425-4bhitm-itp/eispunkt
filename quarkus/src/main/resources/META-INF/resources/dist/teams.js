var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
function savePlayers() {
    return __awaiter(this, void 0, void 0, function* () {
        //AHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHh
        let player1 = document.getElementById("player1");
        let player2 = document.getElementById("player2");
        let player3 = document.getElementById("player3");
        let player4 = document.getElementById("player4");
        let teamName = document.getElementById("teamname").value;
        let players = Array();
        players.push(player1.value);
        players.push(player2.value);
        players.push(player3.value);
        players.push(player4.value);
        yield fetch(`http://localhost:8080/api/team/createTeam?teamName=${teamName}`)
            .then((response) => response.text())
            .then((data) => {
            console.log("Team: " + data);
            sessionStorage.setItem(`team${teamName}`, data);
        })
            .catch((error) => {
            console.error('Error:', error);
        });
        let team = JSON.parse(sessionStorage.getItem(`team${teamName}`));
        let playerData = yield Promise.all(players.map(name => createPlayer(name)));
        for (let i = 0; i < 4; i++) {
            yield addPlayerToTeam(parseInt(team.teamId), players[i]);
        }
        window.location.href = "../pages/chooseTeam.html";
    });
}
function createPlayer(playerName) {
    return __awaiter(this, void 0, void 0, function* () {
        try {
            const response = yield fetch(`http://localhost:8080/api/players/createPlayer?name=${playerName}`);
            const playerData = yield response.text();
            return playerData;
        }
        catch (error) {
            console.error('Error:', error);
        }
    });
}
function addPlayerToTeam(teamId, playerName) {
    return __awaiter(this, void 0, void 0, function* () {
        try {
            const response = yield fetch(`http://localhost:8080/api/players/createPlayerIntoTeam?teamId=${teamId}&name=${playerName}`);
            console.log(yield response.text());
        }
        catch (error) {
            console.error('Error:', error);
        }
    });
}
//# sourceMappingURL=teams.js.map