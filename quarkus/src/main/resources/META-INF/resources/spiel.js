
let team1 = document.getElementById("team1").save
let team2 =document.getElementById("team2").save



const counter = {}; // to store each team's 'counter' value
const pointCounter = {}; //

function addPoint(teamNum) {
    let teamName = document.getElementById(`team${teamNum}`);
    let counterBox = document.getElementById(`counter${teamNum}`)
    if (!(teamNum in counter)) {
        counter[teamNum] = 0;
        pointCounter[teamNum] = 0;
    }

    fetch(`http://localhost:8080/game/modifyScore?team=team${teamNum}&type=+`, {
        method: 'post',
        body: teamName,
    }).then((response) => {
        if (!response.ok) {
            console.log(response.status)
        } else {
            console.log("Point added Successfully")
        }
    })



    counter[teamNum]++;
    if (counter[teamNum] === 1) {
        counterBox.innerText += "3"
    }else if (counter[teamNum] === 2) {
       counterBox.innerText += "6"
    }else if (counter[teamNum] === 3){
        counterBox.innerText += "9"
        pointCounter[teamNum]++
        document.getElementById(`pointCounter${teamNum}`).innerText = pointCounter[teamNum]
        counter[teamNum] = 0;

        // Reset the counterBox display for the next cycle
        counterBox.innerText = "";


    }



    if (pointCounter[teamNum] === 5){

        document.getElementById("winner").innerText = `Team ${teamNum} has won`
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
        counter[teamNum]--
    }

    document.getElementById("teamName1").innerText = team2
}