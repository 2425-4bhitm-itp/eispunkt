const MAX_TEAMS = 2;
function nextTeam(teamNum) {
    document.getElementById("h1").innerText = `Team ${teamNum}`;
    let teamInput = document.getElementById("teamName");
    teamInput.value = "";
    document.getElementById("inputBox").innerHTML = '<input id="player1" placeholder=\"Teamname\"><br><input id="player2" placeholder=\"Teamname\"> <br><input id=\"player3\" placeholder=\"Teamname\"><br><input id=\"player4\" placeholder=\"Teamname\">';
    document.getElementById("buttonBox").innerHTML = `<button id="playButton"><a onclick="savePlayers(${++teamNum})" href="#">Play</a></button>`;
}
function savePlayers(teamNum) {
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
    console.log(players);
    fetch(`http://localhost:8080/api/team/createTeam?teamName=${teamName}`)
        .then((response) => response.text())
        .then((data) => {
        console.log("TeamId: " + data);
        sessionStorage.setItem(`team${teamNum}Id`, data);
    })
        .catch((error) => {
        console.error('Error:', error);
    });
    for (let i = 0; i < 4; i++) {
        fetch(`http://localhost:8080/api/players/createPlayer?name=${players[i]}`)
            .then((response) => response.text())
            .then((playerData) => {
            fetch(`http://localhost:8080/api/team/addPlayer?teamId=${sessionStorage.getItem(`team${teamNum}Id`)}&playerId=${playerData}`)
                .then((response) => response.text())
                .then((data) => {
                console.log(data);
            })
                .catch((error) => {
                console.error('Error:', error);
            });
        }).catch((error) => {
            console.error('Error:', error);
        });
    }
    if (teamNum < MAX_TEAMS) {
        nextTeam(teamNum++);
    }
    else {
        window.location.href = "../pages/game.html";
    }
}
//# sourceMappingURL=teams.js.map