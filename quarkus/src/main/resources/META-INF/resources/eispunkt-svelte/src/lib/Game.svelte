<script lang="ts">

    let t1SubScore = 0;
    let t1SuperScore = 0;
    let t2SubScore = 0;
    let t2SuperScore = 0;

    async function loadGame(){
        try {
            // create game and store ID
            let response1 = await fetch(`http://localhost:8080/api/games/createGame?team1Id=${sessionStorage.getItem("team1Id")}&team2Id=${sessionStorage.getItem("team2Id")}`);
            let gameId = await response1.text();
            sessionStorage.setItem('currentGameId', gameId);
            console.log(sessionStorage.getItem('currentGameId'))

        } catch (error) {
            console.error("Error:", error);
        }
    }


    function addPoint(index: number) {
        if(index == 1){
            t1SubScore++;
            updateText()
            if(t1SubScore > 2){
                t1SuperScore += 2;
                checkWin()
                resetSub()
            }else if(t1SubScore == 2 && t2SubScore == 2){
                t1SuperScore += 1;
                t2SuperScore += 1;
                checkWin()
                resetSub()
            }
        }else{
            t2SubScore++;
            updateText()
            if(t2SubScore > 2){
                t2SuperScore += 2;
                checkWin()
                resetSub()
            }else if(t2SubScore == 2 && t1SubScore == 2){
                t2SuperScore += 1;
                t1SuperScore += 1;
                checkWin()
                resetSub()
            }
        }
    }

    function deletePoint(teamId:number){
        if(teamId == 1){
            t1SubScore--;
        }else{
            t2SubScore--;
        }
    }

    function resetSub(){
        t1SubScore=0;
        t2SubScore=0;
        updateText();
    }

    function updateText(){
        document.getElementById('t1SubScoreText').innerText = `Turnscore: ${t1SubScore}`
        document.getElementById('t2SubScoreText').innerText = `Turnscore: ${t2SubScore}`
        document.getElementById('superScoreCounter').innerText = `${t1SuperScore}-${t2SuperScore}`
    }


    function checkWin(){
        if(t1SuperScore >= 12){
            document.getElementById('game').innerHTML = `<h1>Team 1 Wins</h2>`
            setTimeout(()=>{
                t1SuperScore = 0;
                t2SuperScore = 0;
                resetSub();
                document.getElementById('game').innerHTML = `<h1>Eispunkt</h2>`
            },5000)
        }else if(t2SuperScore >= 12){
            document.getElementById('game').innerHTML = `<h1>Team 2 Wins</h2>`
            setTimeout(()=>{
                t1SuperScore = 0;
                t2SuperScore = 0;
                resetSub();
                document.getElementById('game').innerHTML = `<h1>Eispunkt</h2>`
            },5000)
        }else{
            return;
        }
    }



    loadGame();
</script>


<div id="game">
    <h1>Eispunkt</h1>
</div>
<div id="scoreHeader">
    <h2>Round: 1</h2>
    <h2 id="superScoreCounter"> 0-0</h2>
    <h2 id="turnCounter">Turn: 1</h2>
</div>
<div class="team_outer_box">
    <div class="team_inner_box">
        <h1>Team 1</h1>
        <h2 id="t1SubScoreText">Turnscore: 0</h2>
    </div>
    <div class="points_buttons_outer_box">
        <div class="points_button" onclick={() => addPoint(1)}>+</div>
        <div  class="points_button" onclick={() => deletePoint(1)}>-</div>
    </div>
</div>
<div class="team_outer_box">
    <div class="team_inner_box">
        <h1>Team 2</h1>
        <h2 id="t2SubScoreText">Turnscore: 0</h2>
    </div>
    <div class="points_buttons_outer_box">
        <div class="points_button" onclick={() => addPoint(2)}>+</div>
        <div class="points_button" onclick={() => deletePoint(2)}>-</div>
    </div>
</div>


<style>


    #scoreHeader{
        height:4vh;
        display:flex;
        flex-direction:row;
        justify-content:space-evenly;
        padding-top:1vh;
    }

    #scoreHeader h2{
        width:20vw;
        font-size: 30px;
        text-align:center;
    }

    #game {
        height: 10vh;
        background-color: #45CAAC;
        text-align: center;
        color: white;
        font-weight: bold;
        font-size: 180%;
        font-family: "Protest Strike", serif;
    }

    .team_outer_box {
        padding: 2%;
        height: 30vh;
        display: flex;
        flex-direction: column;
        justify-content: start;
        margin-top: 10%;
    }

    .team_inner_box {
        width: 100%;
        height: 30%;
        justify-content: left;
    }
    .team_inner_box>h1 {
        font-size: 250%;
        margin-bottom: 5%;
    }
    .team_inner_box>h2 {
        font-size: 200%;
    }

    .points_buttons_outer_box {
        width: 100%;
        height: 60%;
        display: flex;
        flex-direction: row;
        justify-content: space-evenly;
        align-items: center;
    }

    .points_button {
        color: white;
        width: 9vh;
        height: 9vh;
        display: flex;
        justify-content: center;
        align-items: center;
        background-color: #7FC8EE;
        font-size: 700%;
        border-radius: 20%;
        box-shadow: 4px 15px 6px rgba(127, 200, 238, 0.5);
        transition: box-shadow 0.3s, transform 0.3s;
        cursor: pointer;
    }

</style>




