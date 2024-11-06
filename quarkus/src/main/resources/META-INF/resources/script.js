function addTeam(teamNum){
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
}

function addPlayer(playerNum) {
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
}

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