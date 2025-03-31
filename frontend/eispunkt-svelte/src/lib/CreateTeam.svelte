<header id="header">Eispunkt</header>

<div id="outer_form_box">
    <h1 class="title">Team erstellen</h1>
    <div class="form">
        <div class="input-container ic1">
            <input id="teamname" class="input" type="text" placeholder=" " />
            <div class="cut cut-long"></div>
            <label for="teamname" class="placeholder">Team name</label>
        </div>
        <div class="input-container ic2">
            <input id="player1" class="input" type="text" placeholder=" " />
            <div class="cut"></div>
            <label for="player1" class="placeholder">Player 1</label>
        </div>
        <div class="input-container ic2">
            <input id="player2" class="input" type="text" placeholder=" " />
            <div class="cut"></div>
            <label for="player2" class="placeholder">Player 2</label>
        </div>
        <div class="input-container ic2">
            <input id="player3" class="input" type="text" placeholder=" " />
            <div class="cut"></div>
            <label for="player3" class="placeholder">Player 3</label>
        </div>
        <div class="input-container ic2">
            <input id="player4" class="input" type="text" placeholder=" " />
            <div class="cut"></div>
            <label for="player4" class="placeholder">Player 4</label>
        </div>
    </div>

    <a id="nextButton" href="#">Speichern</a>
</div>


<style>
    *{
        margin: 0;
    }

    #header{
        background-color: #45CAAC;
        padding: 3%;
        text-align: center;
        color: white;
        font-size: 350%;
        font-weight: bold;
        height: 7.2%;
    }



    body {
        align-items: center;
        background-color: white;
        height: 100vh;
        width: 100vw;
        text-align: center;
    }

    #outer_form_box {
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        margin-top: -10%;
        height: 92.8%;
        width: 100%;
    }

    .form {
        font-size: 50px;
        background-color: white;
        border-radius: 20px;
        box-sizing: border-box;
        height: 80%;
        padding: 20px;
        width: 80%;
    }




    h1{
        text-align: center;
        font-size: 40px;
        margin-top: 10%;

    }

    .input-container {
        height: 10%;
        position: relative;
        width: 100%;
    }

    .ic1 {
        margin-top: 20px;
        margin-bottom: 20%;
    }

    .ic2 {
        margin-top: 60px;
    }

    .input {
        background-color: white;
        border-radius: 12px;
        border: solid 2px #dedddd;
        box-sizing: border-box;
        font-size: 30px;
        color: black;
        text-align: center;
        height: 100%;
        outline: 0;
        padding: 4px 20px 0;
        width: 100%;

    }

    .cut {
        background-color: white;
        border-radius: 10px;
        height: 45px;
        left: 20px;
        position: absolute;
        top: -48px;
        transform: translateY(0);
        transition: transform 200ms;
        width: 160px;
    }

    .cut-long {
        width: 225px;
    }

    .input:focus ~ .cut,
    .input:not(:placeholder-shown) ~ .cut {
        transform: translateY(24px);
    }

    .placeholder {
        color: #65657b;
        font-family: sans-serif;
        left: 20px;
        line-height: 14px;
        pointer-events: none;
        position: absolute;
        transform-origin: 0 50%;
        transition: transform 200ms, color 200ms;
        top: 20px;
        font-size: 20px;
    }

    .input:focus ~ .placeholder,
    .input:not(:placeholder-shown) ~ .placeholder {
        transform: translateY(-30px) translateX(10px) scale(0.75);
    }

    .input:not(:placeholder-shown) ~ .placeholder {
        color: #808097;
    }

    .input:focus ~ .placeholder {
        color: #45CAAC;
    }


    #nextButton{
        text-decoration: none;
        width: 42%;
        padding: 4%;
        font-size: 100%;
        margin-top: 5%;
        border-radius: 10px;
        border: none;
        background-color: #7FC8EE;
        color: white;
        display: flex;
        justify-content: center;
        align-items: center;
        text-align: center;
        font-weight: bold;
        box-shadow: 5px 10px 5px rgba(0, 0, 0, 0.38);
    }



</style>