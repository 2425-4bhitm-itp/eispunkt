<script lang="ts">
    import { onMount } from "svelte";
    import Header from "../../components/Header.svelte";
    import { selectionState } from "../../stores/selectionStore.svelte";
    import { navigationState } from "../../stores/navigationStore.svelte";

    let teams = $state<Array<{ teamId: number; name: string }>>([]);

    onMount(async () => {
        try {
            const response = await fetch("http://localhost:8080/api/team/0", {
                method: "GET",
            });

            if (!response.ok) {
                throw new Error("http fehler status: ${response.status}");
            }

            const teamsResponse = await response.json();
            teams = teamsResponse;
        } catch (error) {
            console.error("Error: ", error);
        }
    });

    function changePane(pane: String) {
        navigationState.currentPane = pane;
    }

    $inspect(selectionState.selectedTeam1);
    $inspect(selectionState.selectedTeam2);
</script>
<div id="body-div">
    <Header />
    <h1>Teams auswählen</h1>

    <div id="selectBox">
        <select
            id="firstTeam"
            class="dropdown"
            bind:value={selectionState.selectedTeam1}
            onchange={() => {
                const selectedTeam = teams.find(
                    (t) => t.teamId === selectionState.selectedTeam1,
                );
                if (selectedTeam) {
                    selectionState.selectedTeam1Name = selectedTeam.name;
                }
            }}
        >
            <option disabled value="" selected>Bitte wählen...</option>
            {#each teams as team (team.teamId)}
                <option value={team.teamId}>{team.name}</option>
            {/each}
        </select>

        <select
            id="secTeam"
            class="dropdown"
            bind:value={selectionState.selectedTeam2}
            onchange={() => {
                const selectedTeam = teams.find(
                    (t) => t.teamId === selectionState.selectedTeam2,
                );
                if (selectedTeam) {
                    selectionState.selectedTeam2Name = selectedTeam.name;
                }
            }}
        >
            <option disabled value="">Bitte wählen...</option>
            {#each teams as team (team.teamId)}
                <option value={team.teamId}>{team.name}</option>
            {/each}
        </select>
    </div>

    <div id="createTeam">
        <h2>neues Team erstellen</h2>
        <a href="/createTeam" id="addButton">+</a>
    </div>

    <div id="next-button-box">
        <a href="/overview" id="nextButton">Weiter</a>
    </div>

</div>

<style>
    #body-div {
        width: 100vw;
        height: 100vh;
        display: flex;
        flex-direction: column;
        padding: 0;
        margin: 0;
    }

    h1 {
        text-align: center;
        font-size: 40px;
        margin-top: 5%;
    }

    select {
        font-size: 25px;
    }

    option {
        font-size: 25px;
    }

    #selectBox {
        display: flex;
        justify-content: space-evenly;
        align-items: center;
        flex-direction: column;
        height: 30vh;
    }

    #firstTeam {
        width: 90%;
        font-size: 30px;
        height: 30%;
        border: 1px solid #ccc;
        border-radius: 8px;
        background-color: white;
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    }

    #firstTeam:focus {
        border-color: rgba(69, 202, 172, 0.33);
        outline: none;
        box-shadow: 0 0 5px rgba(69, 202, 172, 0.33);
    }

    #secTeam {
        width: 90%;
        font-size: 30px;
        height: 30%;
        border: 1px solid #ccc;
        border-radius: 8px;
        background-color: white;
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    }

    #secTeam:focus {
        border-color: rgba(69, 202, 172, 0.33);
        outline: none;
        box-shadow: 0 0 5px rgba(69, 202, 172, 0.33);
    }

    #createTeam {
        display: flex;
        justify-content: space-evenly;
        align-items: center;
        width: 100%;
        height: 20%;
    }

    #createTeam h2 {
        font-size: 150%;
    }

    #addButton {
        width: 13%;
        padding: 1%;
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

    #next-button-box {
        display: flex;
        justify-content: center;
        align-items: center;
        width: 100%;
        height: 20%;
    }

    #nextButton {
        width: 45%;
        padding: 3%;
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
