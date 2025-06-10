<script lang="ts">
    import { onMount } from "svelte";
    import Header from "./Header.svelte";
    import { selectionState } from "../stores/selectionStore.svelte";
    import { navigationState } from "../stores/navigationStore.svelte";

    let teamName = $state("");
    let players = $state(["", "", "", ""]);
    let originalPlayers = $state(["", "", "", ""]);
    let playerIds = $state([0, 0, 0, 0]);
    let isLoading = $state(false);

    onMount(async () => {
        if (selectionState.teamToEdit === 0) {
            console.error("No team selected for editing");
            return;
        }

        await loadTeamData();
        await loadPlayerData();
    });

    async function loadTeamData() {
        try {
            const response = await fetch(
                `http://localhost:8080/api/team/${selectionState.teamToEdit}`,
            );

            if (!response.ok) {
                throw new Error(`Failed to load team: ${response.status}`);
            }

            const team = await response.json();
            teamName = team.name;
        } catch (error) {
            console.error("Error loading team:", error);
        }
    }

    async function loadPlayerData() {
        try {
            const response = await fetch(
                `http://localhost:8080/api/players/team/${selectionState.teamToEdit}`,
            );

            if (response.ok) {
                const teamPlayers = await response.json();
                // Fill the players array with existing player names and IDs
                for (let i = 0; i < 4; i++) {
                    players[i] = teamPlayers[i]?.name || "";
                    originalPlayers[i] = teamPlayers[i]?.name || "";
                    playerIds[i] = teamPlayers[i]?.playerId || 0;
                }
            }
        } catch (error) {
            console.error("Error loading players:", error);
        }
    }

    async function saveChanges() {
        if (isLoading) return;

        try {
            isLoading = true;

            if (teamName.trim() === "") {
                throw new Error("Team name is required");
            }

            const renameResponse = await fetch(
                `http://localhost:8080/api/team/${selectionState.teamToEdit}/${encodeURIComponent(teamName)}`,
                {
                    method: "PATCH",
                },
            );

            if (!renameResponse.ok) {
                throw new Error(
                    `Failed to update team name: ${renameResponse.status}`,
                );
            }

            for (let i = 0; i < 4; i++) {
                if (
                    playerIds[i] !== 0 &&
                    players[i] !== originalPlayers[i] &&
                    players[i].trim() !== ""
                ) {
                    try {
                        const renamePlayerResponse = await fetch(
                            `http://localhost:8080/api/players/${playerIds[i]}?newName=${encodeURIComponent(players[i])}`,
                            {
                                method: "PATCH",
                            },
                        );

                        if (!renamePlayerResponse.ok) {
                            console.error(
                                `Failed to update player ${i + 1}: ${renamePlayerResponse.status}`,
                            );
                        }
                    } catch (error) {
                        console.error(`Error updating player ${i + 1}:`, error);
                    }
                }
            }

            if (selectionState.selectedTeam1 === selectionState.teamToEdit) {
                selectionState.selectedTeam1Name = teamName;
            }
            if (selectionState.selectedTeam2 === selectionState.teamToEdit) {
                selectionState.selectedTeam2Name = teamName;
            }

            navigationState.currentPane = "TeamOverview";
        } catch (error) {
            console.error("Error saving changes:", error);
        } finally {
            isLoading = false;
        }
    }
</script>

<Header></Header>
<div id="outer_form_box">
    <h1>Team bearbeiten</h1>
    <div class="form">
        <div class="input-container ic1">
            <input
                id="teamname"
                class="input"
                type="text"
                placeholder=" "
                bind:value={teamName}
            />
            <div class="cut cut-long"></div>
            <label for="teamname" class="placeholder">Team name</label>
        </div>
        <div class="input-container ic2">
            <input
                id="player1"
                class="input"
                type="text"
                placeholder=" "
                bind:value={players[0]}
            />
            <div class="cut"></div>
            <label for="player1" class="placeholder">Player 1</label>
        </div>
        <div class="input-container ic2">
            <input
                id="player2"
                class="input"
                type="text"
                placeholder=" "
                bind:value={players[1]}
            />
            <div class="cut"></div>
            <label for="player2" class="placeholder">Player 2</label>
        </div>
        <div class="input-container ic2">
            <input
                id="player3"
                class="input"
                type="text"
                placeholder=" "
                bind:value={players[2]}
            />
            <div class="cut"></div>
            <label for="player3" class="placeholder">Player 3</label>
        </div>
        <div class="input-container ic2">
            <input
                id="player4"
                class="input"
                type="text"
                placeholder=" "
                bind:value={players[3]}
            />
            <div class="cut"></div>
            <label for="player4" class="placeholder">Player 4</label>
        </div>
    </div>
    <a id="nextButton" onclick={saveChanges}>
        {isLoading ? "Speichert..." : "Speichern"}
    </a>
</div>

<style>
    * {
        margin: 0;
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

    h1 {
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
        transition:
            transform 200ms,
            color 200ms;
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
        color: #45caac;
    }

    #nextButton {
        text-decoration: none;
        width: 42%;
        padding: 4%;
        font-size: 100%;
        margin-top: 5%;
        border-radius: 10px;
        border: none;
        background-color: #7fc8ee;
        color: white;
        display: flex;
        justify-content: center;
        align-items: center;
        text-align: center;
        font-weight: bold;
        box-shadow: 5px 10px 5px rgba(0, 0, 0, 0.38);
    }
</style>
