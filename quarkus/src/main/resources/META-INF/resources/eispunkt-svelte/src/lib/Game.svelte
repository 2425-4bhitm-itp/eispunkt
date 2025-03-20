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
        <div class="points_button">+</div>
        <div  class="points_button">-</div>
    </div>
</div>
<div class="team_outer_box">
    <div class="team_inner_box">
        <h1>Team 2</h1>
        <h2 id="t2SubScoreText">Turnscore: 0</h2>
    </div>
    <div class="points_buttons_outer_box">
        <div class="points_button">+</div>
        <div class="points_button">-</div>
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

    .next_button_box {
        width: 100%;
        margin-top: 20%;
        display: flex;
        justify-content: center;
    }

    .next_button {
        text-align: center;
        align-items: center;
        justify-content: center;
        width: 40%;
        height: 6vh;
        background-color: #7FC8EE;
        color: white;
        font-size: 500%;
        font-weight: bold;
        border-radius: 25px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
        cursor: pointer;
        transition: background-color 0.3s ease, color 0.3s ease; /* Übergang für Hintergrund und Textfarbe */
    }

    .next_button:hover {
        background-color: #569ac8;
        box-shadow: 0 8px 16px rgba(0, 0, 0, 0.4);
    }

</style>

