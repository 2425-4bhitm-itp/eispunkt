 <script lang="ts">
    import {fade} from "svelte/transition";
    import {cubicInOut} from "svelte/easing";
	import { onMount } from "svelte";
    import {selectedTournament} from "$lib/stores/selectionStore"

    let selectedTeam = $state("")
    let teams = $state(new Array())
    let tourneyTeams = $state(new Array())
    let team1Visible = false;
    let team2Visible = false;


    onMount(async () => {
        let tourneyTeamsResponse = await fetch(`https://it200230.cloud.htl-leonding.ac.at/api/tournaments/team/${$selectedTournament}`);
        
        if(tourneyTeamsResponse.ok){
            tourneyTeams = await tourneyTeamsResponse.json()
        }

        let teamResponse = await fetch(`https://it200230.cloud.htl-leonding.ac.at/api/teams/0`)

        if(teamResponse.ok){
            teams = await teamResponse.json()
        }
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

        let deleteResponse = await fetch(`https://it200230.cloud.htl-leonding.ac.at/api/tournaments/${$selectedTournament}/${team}`,{
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
                    <svg on:click={() => deleteTeam(team.teamId)} width="20" height="20" viewBox="0 0 16 14" xmlns="http://www.w3.org/2000/svg">
                        <path fill="black" d="M6.01761 7L0 12.2654L1.98239 14L8 8.73459L14.0176 14L16 12.2654L9.98239 7L16 1.73459L14.0176 0L8 5.26541L1.98239 0L0 1.73459L6.01761 7Z"/>
                    </svg>
                </div>
            {/each}
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
        width:80%;
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
</style>
