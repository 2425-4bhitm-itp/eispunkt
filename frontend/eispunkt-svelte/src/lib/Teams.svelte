<script lang="ts">
  import Header from "./Header.svelte";
</script>

<link rel="stylesheet"
      href="../../../../eispunkt/eispunkt/quarkus/src/main/resources/META-INF/resources/pages/teams.css">

<Header></Header>

<div id="outer_form_box">
    <div class="form">
        <div class="title">Team erstellen!</div>
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
        <button class="submit">submit</button>
    </div>
</div>

<style>


    h1{
        text-align: center;
        font-size: 550%;
        margin-top: 5%;
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
        justify-content: center;
        align-items: center;
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

    .title {
        color: black;
        font-family: sans-serif;
        font-size: 35px;
        font-weight: 600;
        text-align: center;
    }

    .subtitle {
        color: #eee;
        font-family: sans-serif;
        font-size: 16px;
        font-weight: 600;
        margin-top: 10px;
    }

    .input-container {
        height: 10%;
        position: relative;
        width: 100%;
    }

    .ic1 {
        margin-top: 40px;
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

    .submit {
        background-color: #7FC8EE;
        border-radius: 30px;
        border: 0;
        box-sizing: border-box;
        color: #eee;
        cursor: pointer;
        font-size: 35px;
        height: 8%;
        margin-top: 38px;
        outline: 0;
        text-align: center;
        width: 80%;
        transition: font-size;
        margin-left: 8%;
        padding: 2%;
    }

    .submit:active {
        background-color: #06b;
    }

    .submit:hover {
        background-color: #06c;
        font-size: 70px;
    }
</style>