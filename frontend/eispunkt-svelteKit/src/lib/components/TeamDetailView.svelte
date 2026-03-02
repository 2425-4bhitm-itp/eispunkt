<script lang="ts">
    let { teamId }: { teamId: number } = $props();

    let showModal = $state(false);
    let playerName = $state('');
    let players = $state<any[]>([]);

    // 🔽 NEU
    let mode = $state<'new' | 'existing'>('new');
    let allPlayers = $state<any[]>([]);
    let selectedPlayerId = $state<number | null>(null);

    $effect(() => {
        if (teamId) getPlayers();
    });

    async function getPlayers() {
        const response = await fetch(
            `https://it200230.cloud.htl-leonding.ac.at/api/players/team/${teamId}`
        );
        players = await response.json();
    }

    async function getAllPlayers() {
        const response = await fetch(`https://it200230.cloud.htl-leonding.ac.at/api/players`);
        allPlayers = await response.json();
    }

    function openModal() {
        if (players.length < 4) {
            showModal = true;
            mode = 'new';
            playerName = '';
            selectedPlayerId = null;
            getAllPlayers();
        }
    }

    function closeModal() {
        showModal = false;
    }

    async function savePlayer() {
        if (mode === 'new') {
            if (playerName.trim() === '') {
                alert('Bitte einen Playernamen eingeben!');
                return;
            }

            const playerCreateResponse = await fetch(
                `https://it200230.cloud.htl-leonding.ac.at/api/players/`,
                {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({ name: playerName })
                }
            );

            const playerResponse = await fetch(
                playerCreateResponse.headers.get('location')!
            );
            let player = await playerResponse.json();

            await fetch(
                `https://it200230.cloud.htl-leonding.ac.at/api/players/${player.playerId}?teamId=${teamId}`,
                { method: 'PATCH' }
            );
        } else {
            if (!selectedPlayerId) {
                alert('Bitte einen Spieler auswählen!');
                return;
            }

            await fetch(
                `https://it200230.cloud.htl-leonding.ac.at/api/players/${selectedPlayerId}?teamId=${teamId}`,
                { method: 'PATCH' }
            );
        }

        await getPlayers();
        closeModal();
    }

    async function deletePlayer(id: number) {
        const response = await fetch(
            `https://it200230.cloud.htl-leonding.ac.at/api/players/${id}`,
            { method: 'DELETE' }
        );

        if (response.ok) {
            await getPlayers();
        }
    }
</script>

<div id="body-div">
    <div id="header-box">
        <a id="backArrow" href="/team">
            <svg xmlns="http://www.w3.org/2000/svg" width="50" height="50" viewBox="0 0 24 24">
                <path
                        fill="none"
                        stroke="white"
                        stroke-linecap="round"
                        stroke-linejoin="round"
                        stroke-width="2"
                        d="M5 12h14M5 12l6 6m-6-6l6-6"
                />
            </svg>
        </a>
        <header id="header">Team</header>
    </div>

    <div id="team-details-outer-box">
        <div id="player-header-box">
            <h1>Spieler:</h1>
        </div>

        {#each players as player}
            <div class="team-details">
                <h1>{player.name}</h1>
                <div class="svg-box">
                    <svg
                            onclick={() => deletePlayer(player.playerId)}
                            width="35"
                            height="35"
                            viewBox="0 0 24 24"
                    >
                        <path
                                d="M6 19C6 20.1 6.9 21 8 21H16C17.1 21 18 20.1 18 19V9C18 7.9 17.1 7 16 7H8C6.9 7 6 7.9 6
							9V19ZM18 4H15.5L14.79 3.29C14.61 3.11 14.35 3 14.09 3H9.91C9.65 3 9.39 3.11 9.21 3.29L8.5 4H6C5.45 4 5
							4.45 5 5C5 5.55 5.45 6 6 6H18C18.55 6 19 5.55 19 5C19 4.45 18.55 4 18 4Z"
                                fill="#7FC8EE"
                        />
                    </svg>
                </div>
            </div>
        {/each}
    </div>

    {#if showModal}
        <div class="overlay active">
            <div class="modal">
                <button class="close-btn" onclick={closeModal}>×</button>

                <h2>Spieler hinzufügen</h2>

                <div class="mode-switch">
                    <label>
                        <input type="radio" bind:group={mode} value="new" />
                        Neu
                    </label>
                    <label>
                        <input type="radio" bind:group={mode} value="existing" />
                        Bestehend
                    </label>
                </div>

                {#if mode === 'new'}
                    <label>Name</label>
                    <input type="text" bind:value={playerName} />
                {:else}
                    <label>Spieler auswählen</label>
                    <select bind:value={selectedPlayerId}>
                        <option value={null}>Bitte wählen</option>
                        {#each allPlayers as p}
                            {#if !players.some(tp => tp.playerId === p.playerId)}
                                <option value={p.playerId}>{p.name}</option>
                            {/if}
                        {/each}
                    </select>
                {/if}

                <button class="save-btn" onclick={savePlayer}>Speichern</button>
            </div>
        </div>
    {/if}

    <div id="addButton" class={players.length < 4 ? '' : 'disabled'} onclick={openModal}>
        +
    </div>
</div>

<style>
    @import url('https://fonts.googleapis.com/css2?family=Afacad:ital,wght@0,400..700&display=swap');

    * {
        margin: 0;
        font-family: 'Afacad', sans-serif;
    }

    .disabled {
        background-color: #ccc !important;
        cursor: default !important;
    }

    #body-div {
        width: 100vw;
        height: 100vh;
        display: flex;
        flex-direction: column;
    }

    #header-box {
        height: 10%;
        background-color: #45caac;
        display: flex;
        justify-content: center;
        align-items: center;
        color: white;
    }

    #backArrow {
        position: absolute;
        left: 2%;
    }

    #team-details-outer-box {
        height: 90%;
        display: flex;
        flex-direction: column;
        align-items: center;
        padding-top: 5%;
    }

    .team-details {
        width: 80%;
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 5%;
        margin-top: 5%;
        background-color: #f8f8f8;
        border-radius: 16px;
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
    }

    .overlay {
        position: fixed;
        inset: 0;
        display: flex;
        align-items: center;
        justify-content: center;
        backdrop-filter: blur(6px);
        background-color: rgba(255, 255, 255, 0.4);
        z-index: 10;
    }

    .modal {
        width: 70%;
        padding: 5%;
        background-color: #f8f8f8;
        border-radius: 16px;
        display: flex;
        flex-direction: column;
        align-items: center;
        position: relative;
    }

    .mode-switch {
        display: flex;
        gap: 20px;
        margin-bottom: 20px;
    }

    select,
    input {
        width: 90%;
        font-size: 20px;
        margin-bottom: 10%;
        border: none;
        border-bottom: 2px solid #45caac;
        background: none;
        outline: none;
    }

    .save-btn {
        width: 60%;
        padding: 5%;
        background-color: #7fc8ee;
        color: white;
        border: none;
        border-radius: 16px;
        font-weight: bold;
        cursor: pointer;
    }

    .close-btn {
        position: absolute;
        top: 0;
        right: 10px;
        background: none;
        border: none;
        font-size: 40px;
        cursor: pointer;
    }

    #addButton {
        position: fixed;
        bottom: 40px;
        right: 40px;
        width: 8vh;
        height: 8vh;
        border-radius: 16px;
        background-color: #7fc8ee;
        color: white;
        font-size: 40px;
        display: flex;
        justify-content: center;
        align-items: center;
        cursor: pointer;
    }
</style>