
let team1 = document.getElementById("team1").save
let team2 =document.getElementById("team2").save


const counter = {}; // to store each team's 'counter' value
const pointCounter = {}; //

function addPoint(teamNum){
    let teamName = document.getElementById(`team${teamNum}`);

    if (!(teamNum in counter)) {
        counter[teamNum] = 0;
        pointCounter[teamNum] = 0;
    }

    fetch(`http://localhost:8080/game/modifyScore?team=team${teamNum}&type=+`, {
        method: 'post',
        body: teamName,
    }).then((response) =>{
        if(!response.ok){
            console.log(response.status)
        }else{
            console.log("Point added Successfully")
        }
    })

    counter[teamNum]++;
    if (counter[teamNum] === 9){
        pointCounter[teamNum]++
        counter[teamNum] =""
        document.getElementById(`pointCounter${teamNum}`).innerText = pointCounter[teamNum]
    }
    document.getElementById(`counter${teamNum}`).innerText = counter[teamNum]

    if (pointCounter[teamNum] === 5){

    }

}



function deletePoint(teamNum){
    let teamName = document.getElementById(`team${teamNum}`);

    fetch(`http://localhost:8080/game/modifyScore?team=team${teamNum}&type=-`, {
        method: 'post',
        body: teamName,
    }).then((response) =>{
        if(!response.ok){
            console.log(response.status)
        }else{
            console.log("Point removed Successfully")
        }
    })

    let counterElement = document.getElementById(`counter${teamNum}`);
    let counterText = counterElement.innerText;
    if (counterText.length > 0) {
        counterElement.innerText = counterText.slice(0, -1);
    }

    document.getElementById("teamName1").innerText = team2
}