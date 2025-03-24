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
            console.log("Team: " + data);
            sessionStorage.setItem(`team${teamName}`, data)
        })
        .catch((error) => {
            console.error('Error:', error)
        })

    let team = JSON.parse(sessionStorage.getItem(`team${teamName}`));
    let playerData = await Promise.all(players.map(name => createPlayer(name)));

    for (let i = 0; i < 4; i++) {
        await addPlayerToTeam(parseInt(team.teamId), players[i]);
    }


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

async function addPlayerToTeam(teamId: Number, playerName: String){
    try{
        const response = await fetch(`http://localhost:8080/api/players/createPlayerIntoTeam?teamId=${teamId}&name=${playerName}`)
        console.log(await response.text());
    }catch (error){
        console.error('Error:', error);
    }
}



