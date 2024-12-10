var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
let t1SubScore = 0;
let t1SuperScore = 0;
let t2SubScore = 0;
let t2SuperScore = 0;
function loadGame() {
    return __awaiter(this, void 0, void 0, function* () {
        try {
            // create game and store ID
            let response1 = yield fetch(`http://localhost:8080/api/games/createGame?team1Id=${sessionStorage.getItem("team1Id")}&team2Id=${sessionStorage.getItem("team2Id")}`);
            let gameId = yield response1.text();
            sessionStorage.setItem('currentGameId', gameId);
            console.log(sessionStorage.getItem('currentGameId'));
            // get info (debugging)
            let response2 = yield fetch(`http://localhost:8080/api/games/getGameInfo?gameId=${sessionStorage.getItem("currentGameId")}`);
            let gameInfo = yield response2.text();
            console.log(gameInfo);
            // creates a turn using GameId
            let response3 = yield fetch(`http://localhost:8080/api/turns/newTurn?gameId=${sessionStorage.getItem('currentGameId')}`);
            let turnData = yield response3.text();
            sessionStorage.setItem('currentTurn', turnData);
            console.log(turnData);
        }
        catch (error) {
            console.error("Error:", error);
        }
    });
}
function addPoint(teamId) {
    if (teamId == 1) {
        t1SubScore++;
        updateText();
        if (t1SubScore > 2) {
            t1SuperScore += 2;
            checkWin();
            resetSub();
        }
        else if (t1SubScore == 2 && t2SubScore == 2) {
            t1SuperScore += 1;
            t2SuperScore += 1;
            checkWin();
            resetSub();
        }
    }
    else {
        t2SubScore++;
        updateText();
        if (t2SubScore > 2) {
            t2SuperScore += 2;
            checkWin();
            resetSub();
        }
        else if (t2SubScore == 2 && t1SubScore == 2) {
            t2SuperScore += 1;
            t1SuperScore += 1;
            checkWin();
            resetSub();
        }
    }
}
function deletePoint(teamId) {
    if (teamId == 1) {
        t1SubScore--;
    }
    else {
        t2SubScore--;
    }
}
function resetSub() {
    t1SubScore = 0;
    t2SubScore = 0;
    updateText();
}
function updateText() {
    document.getElementById('t1SubScoreText').innerText = `Turnscore: ${t1SubScore}`;
    document.getElementById('t2SubScoreText').innerText = `Turnscore: ${t2SubScore}`;
    document.getElementById('superScoreCounter').innerText = `${t1SuperScore}-${t2SuperScore}`;
}
function checkWin() {
    if (t1SuperScore >= 12) {
        document.getElementById('game').innerHTML = `<h1>Team 1 Wins</h2>`;
        setTimeout(() => {
            t1SuperScore = 0;
            t2SuperScore = 0;
            resetSub();
            document.getElementById('game').innerHTML = `<h1>Eispunkt</h2>`;
        }, 5000);
    }
    else if (t2SuperScore >= 12) {
        document.getElementById('game').innerHTML = `<h1>Team 2 Wins</h2>`;
        setTimeout(() => {
            t1SuperScore = 0;
            t2SuperScore = 0;
            resetSub();
            document.getElementById('game').innerHTML = `<h1>Eispunkt</h2>`;
        }, 5000);
    }
    else {
        return;
    }
}
loadGame();
//# sourceMappingURL=game.js.map