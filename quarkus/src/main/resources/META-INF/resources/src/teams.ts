const MAX_TEAMS = 2;

// function nextTeam(teamNum: number) {
//
//     document.getElementById("h1").innerText = `Team ${teamNum}`
//
//     let teamInput = document.getElementById("teamName") as HTMLInputElement
//     teamInput.value = "";
//
//     document.getElementById("inputBox").innerHTML = '<input id="player1" placeholder=\"Teamname\"><br><input id="player2" placeholder=\"Teamname\"> <br><input id=\"player3\" placeholder=\"Teamname\"><br><input id=\"player4\" placeholder=\"Teamname\">'
//
//     document.getElementById("buttonBox").innerHTML = `<button id="playButton"><a onclick="savePlayers(${teamNum})" href="#">Play</a></button>`;
//
// }

async function savePlayers() {
    //AHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHh

    let player1 = document.getElementById("player1") as HTMLInputElement
    let player2= document.getElementById("player2") as HTMLInputElement;
    let player3 = document.getElementById("player3") as HTMLInputElement;
    let player4 = document.getElementById("player4") as HTMLInputElement;
    let teamName = (document.getElementById("teamname") as HTMLInputElement).value;

    let players = Array();
    players.push(player1.value);
    players.push(player2.value);
    players.push(player3.value);
    players.push(player4.value);


    await fetch(`http://localhost:8080/api/team/createTeam?teamName=${teamName}`)
        .then((response) => response.text())
        .then((data) => {
            console.log("TeamId: " + data);
            sessionStorage.setItem(`team${teamName}Id`, data)
        })
        .catch((error) => {
            console.error('Error:', error)
        })

    let playerData = await Promise.all(players.map(name => createPlayer(name)));

    for (let i = 0; i < 4; i++) {
        await addPlayerToTeam(parseInt(sessionStorage.getItem(`team${teamName}Id`)),parseInt(playerData[i]));
    }


    // if(teamNum < MAX_TEAMS){
    //     nextTeam(++teamNum);
    // }else{
    //     window.location.href = "../pages/game.html"
    // }

    window.location.href = "../pages/chooseTeam.html"

}

async function createPlayer(playerName: String){
    try {
        const response = await fetch(`http://localhost:8080/api/players/createPlayer?name=${playerName}`);
        const playerData = await response.text();
        return playerData;
    } catch (error) {
        console.error('Error:', error);
    }
}

async function addPlayerToTeam(teamId: Number, playerId: Number){
    try{
        const response = await fetch(`http://localhost:8080/api/team/addPlayer?teamId=${teamId}&playerId=${playerId}`)
        console.log(await response.text());
    }catch (error){
        console.error('Error:', error);
    }
}