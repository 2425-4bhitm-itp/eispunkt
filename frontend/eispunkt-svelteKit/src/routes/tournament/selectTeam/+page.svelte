 <script lang="ts">
    import {fade} from "svelte/transition";
    import {cubicInOut} from "svelte/easing";

    let selectedTeam = "";
    let team1Visible = false;
    let team2Visible = false;



    function selectTeam() {
        const team = parseInt(selectedTeam);

        if (!team) return;

        console.log("(PUT) /api/tournament/{tournamentId}/{teamId}");
        console.log(
            `insert into TOURNAMENT_TEAM (tournament_tournamentid, teams_teamid) values ({tournamentId}, {teamId});`
        );

        if (team === 1) team1Visible = true;
        if (team === 2) team2Visible = true;

        selectedTeam = ""; // reset select
    }

    function deleteTeam(team: any) {
        console.log(
            `delete from TOURNAMENT_TEAM where TOURNAMENT_TOURNAMENTID = {tournamentId} and TEAMS_TEAMID = {teamId};`
        );
        console.log("(DELETE) /api/tournament/{tournamentId}/{teamId}");

        if (team === 1) team1Visible = false;
        if (team === 2) team2Visible = false;
    }

    console.log(`select t from tournament_team tt
         join team t on tt.team_id = t.team_id
         where tt.tournament_id = {tournamentId};`);
    console.log("(GET) /api/tournament/{tournamnetid}/teams");
</script>

<div id="body-div">
    <div id="header-box">
        <a id="backArrow" href="/tournament">
            <svg xmlns="http://www.w3.org/2000/svg" width="50" height="50" viewBox="0 0 24 24">
                <path fill="none" stroke="white" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 12h14M5 12l6 6m-6-6l6-6"/>
            </svg>
        </a>
        <header id="header">Turnier</header>
    </div>
    <div id="content-outer-box">
        <div id="select-box">
            <label for="team-select">Team auswählen</label>

            <select id="team-select" bind:value={selectedTeam} on:change={selectTeam} name="team">
                <option value="" disabled selected>Team auswählen</option>
                <option value="1" disabled={team1Visible}>Eispunkt</option>
                <option value="2" disabled={team2Visible}>Eisbär</option>
            </select>
        </div>


        <div id="display-box">
            {#if team1Visible}
                <div class="team-box" id="team1" transition:fade={{ duration: 250, easing: cubicInOut }}>
                    <h1>Eispunkt</h1>
                    <svg on:click={() => deleteTeam(1)} width="20" height="20" viewBox="0 0 16 14" xmlns="http://www.w3.org/2000/svg">
                        <path fill="black" d="M6.01761 7L0 12.2654L1.98239 14L8 8.73459L14.0176 14L16 12.2654L9.98239 7L16 1.73459L14.0176 0L8 5.26541L1.98239 0L0 1.73459L6.01761 7Z"/>
                    </svg>
                </div>
            {/if}

            {#if team2Visible}
                <div class="team-box" id="team2" transition:fade={{ duration: 250, easing: cubicInOut }}>
                    <h1>Eisbär</h1>
                    <svg on:click={() => deleteTeam(2)} width="20" height="20" viewBox="0 0 16 14" xmlns="http://www.w3.org/2000/svg">
                        <path fill="black" d="M6.01761 7L0 12.2654L1.98239 14L8 8.73459L14.0176 14L16 12.2654L9.98239 7L16 1.73459L14.0176 0L8 5.26541L1.98239 0L0 1.73459L6.01761 7Z"/>
                    </svg>
                </div>
            {/if}
        </div>

        <a id="next-button" href="/game">
            <svg width="100" height="100" viewBox="0 0 43 36" xmlns="http://www.w3.org/2000/svg">
                <path d="M19.35 23.85L27.7171 18.6C28.2009 18.3 28.2009 17.7 27.7171 17.4L19.35 12.15C18.7588 11.775 17.9167 12.135 17.9167 12.75V23.25C17.9167 23.865 18.7588 24.225 19.35 23.85ZM21.5 3C11.61 3 3.58337 9.72 3.58337 18C3.58337 26.28 11.61 33 21.5 33C31.39 33 39.4167 26.28 39.4167 18C39.4167 9.72 31.39 3 21.5 3ZM21.5 30C13.5988 30 7.16671 24.615 7.16671 18C7.16671 11.385 13.5988 6 21.5 6C29.4013 6 35.8334 11.385 35.8334 18C35.8334 24.615 29.4013 30 21.5 30Z" fill="#7fc8ee"/>
            </svg>
        </a>
    </div>
</div>



<style>
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

    #header-box {
        width: 100%;
        height: 10%;
        display: flex;
        background-color: #45CAAC;
        align-items: center;
        justify-content: center;
    }
    #header-box header {
        color: white;
        font-size: 400%;
        font-weight: bold;
        font-family: "Afacad", sans-serif;
    }

    #backArrow {
        position: absolute;
        left: 2%;
    }

    #content-outer-box {
        width: 100%;
        height: 90%;
        display: flex;
        justify-content: start;
        align-items: center;
        flex-direction: column;
    }

    #select-box {
        width: 100%;
        height: 30%;
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
    }
    label {
        font-size: 40px;
        top: 2%;
        font-weight: bold;
    }
    select {
        width: 80%;
        font-size: 35px;
        border: 1px solid #ccc;
        border-radius: 12px;
        background-color: white;
        box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
        transition: all 0.2s ease;
        cursor: pointer;
        margin-top: 10%;
        font-family: "Afacad", sans-serif;
        color: rgba(0, 0, 0, 0.27);
    }

    #display-box {
        width: 100%;
        height: 70%;
        display: flex;
        flex-direction: column;
        align-items: center;
    }

    .team-box {
        width: 70%;
        height: 10%;
        padding: 5%;
        margin-bottom: 5%;
        display: flex;
        justify-content: space-between;
        align-items: center;
        font-size: 15px;
        background-color: #f8f8f8;
        border-radius: 16px;
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
    }

    #next-button {
        width: 10vh;
        height: 10vh;
        display: flex;
        justify-content: center;
        align-items: center;
        position: fixed;
        bottom: 30px;
        right: 30px;
        border-radius: 16px;
    }
</style>
