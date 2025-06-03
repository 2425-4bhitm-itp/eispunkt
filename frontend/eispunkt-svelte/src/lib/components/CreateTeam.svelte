<script lang="ts">
    import Header from "./Header.svelte";
    import { navigationState } from "../stores/navigationStore.svelte";

    let teamName = "";
    let players = ["", "", "", ""];
    let isLoading = false;

    async function savePlayers() {
        try {
            isLoading = true;

            //TODO: replace this error handling with highlighting on the input fields
            const nonEmptyPlayers = players.filter(
                (name) => name.trim() !== "",
            );

            if (teamName.trim() === "") {
                throw new Error("Team name is required");
            }

            if (nonEmptyPlayers.length === 0) {
                throw new Error("At least one player is required");
            }

            const teamResponse = await fetch(
                `http://localhost:8080/api/team/createTeam?teamName=${encodeURIComponent(teamName)}`,
            );
            const teamData = await teamResponse.text();
            const team = JSON.parse(teamData);

            await Promise.all(
                nonEmptyPlayers.map(async (name) => {
                    await createPlayerIntoTeam(parseInt(team.teamId), name);
                }),
            );
        } catch (err) {
            console.error("Error:", err);
        } finally {
            isLoading = false;
        }

        navigationState.currentPane = "ChooseTeam"
    }


    async function createPlayerIntoTeam(teamId: number, playerName: string) {
        const response = await fetch(
            `http://localhost:8080/api/players/createPlayerIntoTeam?teamId=${teamId}&name=${encodeURIComponent(playerName)}`,
        );
        console.log(await response.text());
    }
</script>

<Header></Header>

<div id="outer_form_box">
    <h1 class="title">Team erstellen</h1>
    <div class="form">
        <div class="input-container ic1">
            <label for="teamname">Team Name:</label>
            <input
                id="teamname"
                type="text"
                bind:value={teamName}
                placeholder="Enter team name"
            />
        </div>

        {#each players as _, i}
            <div class="input-container ic2">
                <label for="player{i + 1}">Player {i + 1}:</label>
                <input
                    id="player{i + 1}"
                    type="text"
                    bind:value={players[i]}
                    placeholder={`Enter player ${i + 1} name`}
                />
            </div>
        {/each}
    </div>

    <a id="nextButton" onclick={savePlayers} href="#">Speichern</a>
</div>

<style>
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

    input {
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

    input:focus ~ .cut,
    input:not(:placeholder-shown) ~ .cut {
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

    input:focus ~ .placeholder,
    input:not(:placeholder-shown) ~ .placeholder {
        transform: translateY(-30px) translateX(10px) scale(0.75);
    }

    input:not(:placeholder-shown) ~ .placeholder {
        color: #808097;
    }

    input:focus ~ .placeholder {
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
