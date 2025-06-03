<div class="circle">
    <h1>Willkommen bei<br><span>Eispunkt</span></h1>
</div>
<div class="dropdown-container">
    <label  class="dropdown-label">Selektieren Sie einen Spielmodus</label>
    <select id="game-mode" class="dropdown">
        <option value="" disabled selected>Select game</option>
        <option value="spiel1">spiel</option>
        <option value="spiel2">spiel</option>
        <option value="spiel3">spiel</option>
    </select>
</div>


<style>


    .circle {
        position: relative;
        left: 30%;
        transform: translateX(-50%);
        width: 140vw;
        height: 80vw;
        background-color: #45CAAC;
        border-radius: 0 0 80% 50%;
        display: flex;
        justify-content: center;
        align-items: center;


    }

    .circle h1 {
        color: white;
        font-size: 30px;
        text-align: center;
        line-height: 1.4;
        margin-left: 5%;
        margin-top: -5%;
        font-weight: lighter;
        font-family: "Protest Strike", serif;
        font-weight: 400;
        font-style: normal
    }

    .circle h1 span {
        font-weight: bold;
        font-size: 70px;
        font-family: "Protest Strike", serif;
        font-weight: 400;
        font-style: normal;
        margin-left: 12%;

    }


    .dropdown-container {
        width: 100%;
        margin-bottom: 20px;
    }


    .dropdown-label {
        font-size: 150%;
        font-weight: bold;
        margin-bottom: 8px;
        display: block;
        margin-top: 10%;
        margin-left: 5%;
        margin-bottom: 5%;
        font-family: "Afacad", serif;
        font-optical-sizing: auto;
        font-style: normal;

    }


    .dropdown {
        width: 90%;
        padding: 3%;
        margin-left: 4%;
        font-size: 100%;
        border: 1px solid #ccc;
        border-radius: 8px;
        background-color: white;
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);

    }


    .dropdown:focus {
        border-color: rgba(69, 202, 172, 0.33) ;
        outline: none;
        box-shadow: 0 0 5px rgba(69, 202, 172, 0.33);
    }

    #startButton{
        width: 45%;
        padding: 4%;
        margin-top: 60%;
        margin-left: 23%;
        font-size: 300%;
        border-radius: 20px;
        border: none;
        background-color: #7FC8EE;
        color: white;
        display: flex;
        justify-content: center;
        align-items: center;
        text-decoration: none;
        text-align: center;
        font-weight: bold;
        box-shadow: 1px 15px 15px 7px rgba(0, 0, 0, 0.16);

    }


</style>