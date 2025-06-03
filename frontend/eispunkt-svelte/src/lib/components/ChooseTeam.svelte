<script lang="ts">
  import { onMount } from "svelte";
  import Header from "./Header.svelte";
  import { selectionState } from "../stores/selectionStore.svelte"
  import { navigationState } from "../stores/navigationStore.svelte";

  let teams = $state<Array<{ teamId: number; name: string }>>([]);

  onMount(async () => {
    try {
      const response = await fetch(
        "http://localhost:8080/api/team/getAllTeams"
      );

      if (!response.ok) {
        throw new Error("http fehler status: ${response.status}");
      }

      const teamsResponse = await response.json();
      teams = teamsResponse;
    } catch (error) {
      console.error("Error: ", error);
    }
  });

  function changePane(pane:String){
    navigationState.currentPane = pane;
  }

  $inspect(selectionState.selectedTeam1)
  $inspect(selectionState.selectedTeam2)
</script>

<div>
  <Header/>
  <h1>Teams auswählen</h1>

  <div id="selectBox">
    <select id="firstTeam" class="dropdown" bind:value={selectionState.selectedTeam1}>
      <option disabled value="" selected>Bitte wählen...</option>
      {#each teams as team (team.teamId)}
        <option value={team.teamId}>{team.name}</option>
      {/each}
    </select>

    <select id="secTeam" class="dropdown" bind:value={selectionState.selectedTeam2}>
      <option disabled value="">Bitte wählen...</option>
      {#each teams as team (team.teamId)}
        <option value={team.teamId}>{team.name}</option>
      {/each}
    </select>
  </div>

  <div id="createTeam">
    <h2>neues Team erstellen</h2>
    <button id="addButton" onclick={()=>changePane("createTeam")}>+</button>
  </div>

  <a id="nextButton" href="teamOverview.html">Weiter</a>
</div>

<style>
  h1 {
    text-align: center;
    font-size: 40px;
    margin-top: 5%;
  }

  #selectBox {
    display: block;
    font-size: 20px;
  }

  #firstTeam {
    width: 90%;
    margin-left: 5%;
    font-size: 50px;
    border: 1px solid #ccc;
    border-radius: 8px;
    background-color: white;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    margin-top: 15%;
  }

  #firstTeam:focus {
    border-color: rgba(69, 202, 172, 0.33);
    outline: none;
    box-shadow: 0 0 5px rgba(69, 202, 172, 0.33);
  }

  #secTeam {
    width: 90%;
    margin-left: 5%;
    font-size: 50px;
    border: 1px solid #ccc;
    border-radius: 8px;
    background-color: white;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    margin-top: 20%;
  }

  #secTeam:focus {
    border-color: rgba(69, 202, 172, 0.33);
    outline: none;
    box-shadow: 0 0 5px rgba(69, 202, 172, 0.33);
  }

  #createTeam {
    display: flex;
    width: 100%;
    margin-top: 15%;
    justify-content: center;
  }

  #addButton {
    width: 13%;
    padding: 1%;
    margin-left: 15%;
    font-size: 150%;
    border-radius: 25px;
    border: none;
    background-color: #7fc8ee;
    color: white;
    display: flex;
    justify-content: center;
    align-items: center;
    text-decoration: none;
    text-align: center;
    font-weight: bold;
  }

  #createTeam h2 {
    font-size: 150%;
    margin-top: 2%;
  }

  #nextButton {
    width: 45%;
    padding: 3%;
    margin-top: 50%;
    margin-left: 23%;
    font-size: 200%;
    border-radius: 20px;
    border: none;
    background-color: #7fc8ee;
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
