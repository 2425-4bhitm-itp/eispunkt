let t1SubScore = 0;
let t1SuperScore = 0;
let t2SubScore = 0;
let t2SuperScore = 0;
function loadGame() {
    fetch(`http://localhost:8080/api/games/createGame?team1Id=${sessionStorage.getItem("team1Id")}&team2Id=${sessionStorage.getItem("team2Id")}`)
        .then((response) => response.text())
        .then((data) => {
        sessionStorage.setItem('currentGameId', data);
    })
        .catch((error) => {
        console.error("Error:", error);
    });
    fetch(`http://localhost:8080/api/games/getGameInfo?gameId=${sessionStorage.getItem("currentGameId")}`)
        .then((response) => response.text())
        .then((data => {
        console.log(data);
    }));
    fetch(`http://localhost:8080/api/turns/newTurn?gameId=${sessionStorage.getItem('currentGameId')}`)
        .then((response) => response.text())
        .then((data) => {
        sessionStorage.setItem('currentTurn', data);
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