 <script lang="ts">
    import {fade} from "svelte/transition";
    import {cubicInOut} from "svelte/easing";
	import { onMount } from "svelte";
    import {selectedTournament} from "$lib/stores/selectionStore"

    let selectedTeam = $state("")
    let teams = $state(new Array())
    let tourneyTeams = $state(new Array())

    onMount(async () => {
        let tourneyTeamsResponse = await fetch(`https://it200230.cloud.htl-leonding.ac.at/api/tournaments/team/${$selectedTournament}`);
        
        if(tourneyTeamsResponse.ok){
            tourneyTeams = await tourneyTeamsResponse.json()
        }


        let teamResponse = await fetch(`https://it200230.cloud.htl-leonding.ac.at/api/teams/0`)

        if(teamResponse.ok){
            teams = await teamResponse.json()
        }

        teams = teams.filter(t => !tourneyTeams.find(tt => tt.teamId == t.teamId))
    })


    async function selectTeam() {
        const team = parseInt(selectedTeam);

        if (!team) return;

        let selectResponse = await fetch(`https://it200230.cloud.htl-leonding.ac.at/api/tournaments/${$selectedTournament}/${team}`,{
            method: "POST"
        });

        if(selectResponse.ok){
            tourneyTeams.push(teams.find(t => t.teamId == parseInt(selectedTeam)))
            teams = teams.filter(t => t.teamId != parseInt(selectedTeam))
        }
        

        selectedTeam = ""; 
    }

    async function deleteTeam(team: Number) {
        if(!team) return;

        let deleteResponse = await fetch(`https://it200230.cloud.htl-leonding.ac.at/api/tournaments/remove/${$selectedTournament}/${team}`,{
            method: "DELETE"
        });

        if(deleteResponse.ok){
            tourneyTeams = tourneyTeams.filter(t => t.teamId != team)
        }
    }
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
                {#each teams as team}
                    <option value="{team.teamId}">{team.name}</option>
                {/each}
            </select>
        </div>


        <div id="display-box">
            {#each tourneyTeams as team}
                <div class="team-box" transition:fade={{ duration: 250, easing: cubicInOut }}>
                    <h1>{team.name}</h1>
                    <svg on:click={() => deleteTeam(team.teamId)} width="30" height="30" viewBox="0 0 16 14" xmlns="http://www.w3.org/2000/svg">
                        <path fill="black" d="M6.01761 7L0 12.2654L1.98239 14L8 8.73459L14.0176 14L16 12.2654L9.98239 7L16 1.73459L14.0176 0L8 5.26541L1.98239 0L0 1.73459L6.01761 7Z"/>
                    </svg>
                </div>
            {/each}
        </div>

        <a id="next-button" href="/tournament/gameOverview">
            <svg width="100" height="100" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                <path d="M12 2C6.48 2 2 6.48 2 12C2 17.52 6.48 22 12 22C17.52 22 22 17.52 22 12C22 6.48 17.52 2 12 2ZM9.5 14.67V9.33C9.5 8.54 10.38 8.06 11.04 8.49L15.19 11.16C15.8 11.55 15.8 12.45 15.19 12.84L11.04 15.51C10.38 15.94 9.5 15.46 9.5 14.67Z" fill="#7FC8EE"/>
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
        overflow:scroll;
        align-items: center;
    }

    .team-box {
        width: 70%;
        height: 10%;
        padding: 5%;
        margin-bottom: 5%;
        display: flex;

        align-items: center;
        font-size: 15px;
        background-color: #f8f8f8;
        border-radius: 16px;
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
    }
    .team-box h1{
        width:75%;
        font-size:20px;
    }

    #next-button {
        width: 10vh;
        height: 10vh;
        display: flex;
        justify-content: center;
        align-items: center;
        position: fixed;
        bottom: 30px;
        right: 15px;
        border-radius: 16px;
    }

    svg{
        cursor:pointer;
    }

    @media (min-width: 1024px) {
        .team-box h1{
            width:80%;
            font-size:40px;
        }

        .team-box svg{
            width: 30px;
            height: 30px;
        }

        label {
            font-size: 60px;
            top: 2%;
            font-weight: bold;
        }

        select {
            width: 80%;
            font-size: 45px;
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

        #next-button svg{
            width: 120px;
            height: 120px;
        }
    }


    @media (min-width: 1748px) {
        #display-box {
            width: 100%;
            height: 70%;
            display: flex;
            flex-direction: column;
            overflow:scroll;
            align-items: center;
            margin-top: 12%;
        }

        label {
            font-size: 90px;
            top: 2%;
            font-weight: bold;
            margin-top: 10%;
        }

        select {
            width: 80%;
            font-size: 60px;
            border: 1px solid #ccc;
            border-radius: 12px;
            background-color: white;
            box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
            transition: all 0.2s ease;
            cursor: pointer;
            margin-top: 5%;
            font-family: "Afacad", sans-serif;
            color: rgba(0, 0, 0, 0.27);
        }

    }
</style>
