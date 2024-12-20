var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
const MAX_TEAMS = 2;
function nextTeam(teamNum) {
    document.getElementById("h1").innerText = `Team ${teamNum}`;
    let teamInput = document.getElementById("teamName");
    teamInput.value = "";
    document.getElementById("inputBox").innerHTML = '<input id="player1" placeholder=\"Teamname\"><br><input id="player2" placeholder=\"Teamname\"> <br><input id=\"player3\" placeholder=\"Teamname\"><br><input id=\"player4\" placeholder=\"Teamname\">';
    document.getElementById("buttonBox").innerHTML = `<button id="playButton"><a onclick="savePlayers(${teamNum})" href="#">Play</a></button>`;
}
function savePlayers(teamNum) {
    return __awaiter(this, void 0, void 0, function* () {
        //AHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHh
        let player1 = document.getElementById("player1");
        let player2 = document.getElementById("player2");
        let player3 = document.getElementById("player3");
        let player4 = document.getElementById("player4");
        let teamName = document.getElementById("teamName").value;
        let players = Array();
        players.push(player1.value);
        players.push(player2.value);
        players.push(player3.value);
        players.push(player4.value);
        yield fetch(`http://localhost:8080/api/team/createTeam?teamName=${teamName}`)
            .then((response) => response.text())
            .then((data) => {
            console.log("TeamId: " + data);
            sessionStorage.setItem(`team${teamNum}Id`, data);
        })
            .catch((error) => {
            console.error('Error:', error);
        });
        let playerData = yield Promise.all(players.map(name => createPlayer(name)));
        for (let i = 0; i < 4; i++) {
            yield addPlayerToTeam(parseInt(sessionStorage.getItem(`team${teamNum}Id`)), parseInt(playerData[i]));
        }
        if (teamNum < MAX_TEAMS) {
            nextTeam(++teamNum);
        }
        else {
            window.location.href = "../pages/game.html";
        }
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
function addPlayerToTeam(teamId, playerId) {
    return __awaiter(this, void 0, void 0, function* () {
        try {
            const response = yield fetch(`http://localhost:8080/api/team/addPlayer?teamId=${teamId}&playerId=${playerId}`);
            console.log(yield response.text());
        }
        catch (error) {
            console.error('Error:', error);
        }
    });
}
//# sourceMappingURL=teams.js.map