

let inputCountTeam1 = 1;

function addInputTeam1() {
    if (inputCountTeam1 < 3) {
        inputCountTeam1++;
        const container = document.getElementById('player_box_team1');
        const newInputGroup = document.getElementById('input-group_team1');

        const newInput = document.createElement('input');
        newInput.placeholder = `Player ${inputCountTeam1}`;
        newInput.className = 'input_players'

        newInputGroup.appendChild(newInput);

        if (inputCountTeam1 === 3) {
            document.querySelector('.add-btn').disabled = true;
        }
    }
}

let inputCountTeam2 = 1;

function addInputTeam2() {
    if (inputCountTeam2 < 3) {
        inputCountTeam2++;
        const container = document.getElementById('player_box_team2');
        const newInputGroup = document.getElementById('input-group_team2');

        const newInput = document.createElement('input');
        newInput.placeholder = `Player ${inputCountTeam2}`;
        newInput.className = 'input_players'

        newInputGroup.appendChild(newInput);

        if (inputCountTeam2 === 3) {
            document.querySelector('.add-btn').disabled = true;
        }
    }
}

document.getElementById('next_button').addEventListener("click", saveTeamAndPlayers);

function saveTeamAndPlayers() {

    // TEAM 1
    const team1_name = document.getElementById("team1").value;
    const team1_players = [];
    document.querySelectorAll('#input-group_team1 .input_players').forEach(input => {
       team1_players.push(input.value);
    });

    sessionStorage.setItem("team1_name", team1_name);
    sessionStorage.setItem("team1_players", JSON.stringify(team1_players));



    // TEAM 2
    const team2_name = document.getElementById("team2").value;
    const team2_players = [];
    document.querySelectorAll('#input-group_team2 .input_players').forEach(input => {
        team2_players.push(input.value);
    });

    sessionStorage.setItem("team2_name", team2_name);
    sessionStorage.setItem("team2_players", JSON.stringify(team2_players));
}





/*function addTeam(teamNum){
    let teamName = document.getElementById(`team${teamNum}`);

    fetch(`http://localhost:8080/team/addTeam?teamName=${teamName}`, {
        method: 'POST'
    }).then((response) =>{
        if(!response.ok){
            console.log(response)
        }else{
            console.log("Team added Successfully")
        }
    })
}*/




/*function addInputPlayer(teamNum) {
    if (inputCountTeam1 < 3) {
        inputCountTeam1++;
        const container = document.getElementById(`player_box_team${teamNum}`);
        const newInputGroup = document.getElementById(`input-group_team${teamNum}`);

        const newInput = document.createElement('input');
        newInput.placeholder = `Player ${inputCountTeam1}`;
        newInput.className = 'input_players'

        newInputGroup.appendChild(newInput);

        if (inputCountTeam1 === 3) {
            document.querySelector('.add-btn').disabled = true;
        }
    }
}+/


/*function addPlayer(playerNum) {
    let playerName = document.getElementById(`player${playerNum}`);

    fetch(`http://localhost:8080/player/addPlayer?playerName=${playerName}`, {
        method: 'POST'
    }).then((response) =>{
        if(!response.ok){
            console.log(response)
        }else{
            console.log("Player added Successfully")
        }
    })
}*/