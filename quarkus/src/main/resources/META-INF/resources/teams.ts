function nextTeam(){
    let player1 = document.getElementById("player1").innerText.valueOf()
    let player2 = document.getElementById("player2").innerText.valueOf()
    let player3 = document.getElementById("player3").innerText.valueOf()
    let player4 = document.getElementById("player4").innerText.valueOf()

    document.getElementById("h1").innerText = "Team 2"

    document.getElementById("inputBox").innerHTML = '<input id="t2player1" placeholder=\"Teamname\"><br><input id=t2player" placeholder=\"Teamname\"> <br><input id=\"t2player3\" placeholder=\"Teamname\"><br><input id=\"t2player4\" placeholder=\"Teamname\">'
    document.getElementById("nextButton").innerHTML = '<button id="playButton"><a onclick="savePlayers()" href="game.html">Play</a></button>'

}