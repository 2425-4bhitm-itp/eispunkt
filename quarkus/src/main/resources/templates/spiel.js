
let team1 = document.getElementById("team1").save
let team2 =document.getElementById("team2").save


function addPoint(teamNum){
    let teamName = document.getElementById(`team${teamNum}`);
    let counter ="|"
    fetch("http://localhost:8080/game/modifyScore?team=[team${teamNum}]&type=[+]", {
        method: 'post',
        body: teamName,
    }).then((response) =>{
        if(!response.ok){
            console.log(response.status)
        }else{
            console.log("Point added Successfully")
        }
    })

    document.getElementById("counter1").innerText += counter
}

function deletePoint(teamNum){
    let teamName = document.getElementById(`team${teamNum}`);

    fetch("http://localhost:8080/game/modifyScore?team=[team${teamNum}]&type=[-]", {
        method: 'post',
        body: teamName,
    }).then((response) =>{
        if(!response.ok){
            console.log(response.status)
        }else{
            console.log("Point removed Successfully")
        }
    })

    let counterElement = document.getElementById("counter1");
    let counterText = counterElement.innerText;
    if (counterText.length > 0) {
        counterElement.innerText = counterText.slice(0, -1);
    }
}