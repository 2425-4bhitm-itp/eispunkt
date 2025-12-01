<script>

</script>

<div id="body-div">

    <div class="header-box">
        <h1>
            Willkommen bei<br/>
            <span style="font-size: 65px">Eispunkt</span>
        </h1>
    </div>

    <div class="selection-box">
        <div class="subtitle-box">
            <h2>Spielmodus auswählen:</h2>
        </div>

        <a href="/team" class="button">
            <h1>Turnier</h1>
        </a>

        <a class="button deactivated">
            <h1>Mannschaftsspiel</h1>
        </a>

        <a class="button deactivated">
            <h1>Freundschaftsspiel</h1>
        </a>
    </div>

</div>

<style>
    @import url("https://fonts.googleapis.com/css2?family=Afacad:ital,wght@0,400..700;1,400..700&display=swap");
    @import url("https://fonts.googleapis.com/css2?family=Protest+Strike&display=swap");

    #body-div{
        width: 100vw;
        height: 100vh;
        display: flex;
        flex-direction: column;
        padding: 0;
        margin: 0;
        overflow: hidden;
        font-family: "Afacad", sans-serif;
    }

    .header-box {
        width: 100%;
        height: 30%;
        background-color: #45caac;
        border-radius: 0 0 80% 0;
        display: flex;
        padding-left: 5%;
        justify-content: start;
        align-items: center;
    }
    .header-box h1 {
        color: white;
        font-size: 35px;
        text-align: left;
        font-weight: lighter;
        font-family: "Protest Strike", serif;
        font-style: normal;
    }

    .selection-box {
        width: 100%;
        height: 70%;
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
    }

    .subtitle-box {
        width: 80%;
        height: 15%;
        display: flex;
        justify-content: start;
        align-items: center;
        gap: 3%;
    }
    .subtitle-box h2 {
        font-family: "Afacad", sans-serif;
        font-size: 30px;
    }

    .button {
        width: 80%;
        height: 15%;
        display: flex;
        flex-direction: column;
        justify-content: space-evenly;
        align-items: start;
        background-color: #7fc8ee;
        border-radius: 20px;
        cursor: pointer;
        text-decoration: none;
        margin-bottom: 5%;
        padding-left: 5%;
    }

    .deactivated{
        background-color:#858585;
        cursor:default;
    }

    .deactivated:hover{
        background-color:#858585 !important;
    }

    .button h1 {
        color: white;
        font-family: "Afacad", sans-serif;
        font-size: 30px;
    }
    .button:hover {
        background-color: #7fe3ee;
    }
</style>
