<script lang="ts">
	import { onMount } from 'svelte';
	import { selectionState } from '../../stores/selectionStore.svelte';
	import { navigationState } from '../../stores/navigationStore.svelte';
	import { gameState } from '../../stores/gameStore.svelte';
	import Header from '../../components/Header.svelte';

	let team1Players = $state<any[]>([]);
	let team2Players = $state<any[]>([]);

	async function loadPlayers() {
		try {
			const team1Response = await fetch(
				`http://localhost:8080/api/players/team/${selectionState.selectedTeam1}`,
				{
					method: 'GET'
				}
			);

			if (team1Response.ok) {
				team1Players = await team1Response.json();
			}

			const team2Response = await fetch(
				`http://localhost:8080/api/players/team/${selectionState.selectedTeam2}`,
				{
					method: 'GET'
				}
			);

			if (team2Response.ok) {
				team2Players = await team2Response.json();
			}
		} catch (error) {
			console.error('Error loading players: ', error);
		}
	}

	onMount(async () => {
		await loadPlayers();
	});

	async function startGame() {
		try {
			const gameResponse = await fetch('http://localhost:8080/api/games', {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json'
				}
			});

			if (!gameResponse.ok) {
				throw new Error(`Failed to create game: ${gameResponse.status}`);
			}

			const game = await gameResponse.json();
			console.log('Game created:', game);
			gameState.gameId = game.gameId;

			const addTeam1Response = await fetch(
				`http://localhost:8080/api/games/${game.gameId}/${selectionState.selectedTeam1}`,
				{
					method: 'POST'
				}
			);

			if (!addTeam1Response.ok) {
				throw new Error(`Failed to add team1 to game: ${addTeam1Response.status}`);
			}

			const addTeam2Response = await fetch(
				`http://localhost:8080/api/games/${game.gameId}/${selectionState.selectedTeam2}`,
				{
					method: 'POST'
				}
			);

			if (!addTeam2Response.ok) {
				throw new Error(`Failed to add team2 to game: ${addTeam2Response.status}`);
			}

			window.location.href = '/game'
		} catch (error) {
			console.error('Error starting game:', error);
		}
	}

	function editTeam(teamIndex: number) {
		if (teamIndex === 1) {
			selectionState.teamToEdit = selectionState.selectedTeam1;
		} else {
			selectionState.teamToEdit = selectionState.selectedTeam2;
		}
		navigationState.currentPane = 'EditTeam';
	}
</script>

<Header></Header>
<h1>gewählte Teams</h1>

<div id="firstTeam">
    <div class="teamHeader">
        <label id="team1">{selectionState.selectedTeam1Name}</label>

        <a class="editSvg" onclick={() => editTeam(1)}>
            <img src="/src/lib/assets/edit-icon.svg" alt="Edit team" />
        </a>
    </div>
	{#if team1Players.length > 0}
		{#each team1Players.slice(0, 4) as player, index}
			<h2 id="team1_player{index + 1}">
				{player.name || player.playerName || 'Player ' + (index + 1)}
			</h2>
		{/each}
	{/if}
</div>

<div id="secTeam">
    <div class="teamHeader">
        <label id="team1">{selectionState.selectedTeam2Name}</label>

        <a class="editSvg" onclick={() => editTeam(2)}>
            <img src="/src/lib/assets/edit-icon.svg" alt="Edit team" />
        </a>
    </div>

	{#if team2Players.length > 0}
		{#each team2Players.slice(0, 4) as player, index}
			<h2 id="team2_player{index + 1}">
				{player.name || player.playerName || 'Player ' + (index + 1)}
			</h2>
		{/each}
	{/if}

	<a id="playButton" onclick={startGame}>Spielen</a>
</div>

<style>
	h1 {
		text-align: center;
		font-size: 40px;
        font-family: "Afacad", sans-serif;

    }
	label {
		font-size: 125%;
		font-weight: bold;
        font-family: "Afacad", sans-serif;

    }

	h2 {
		font-size: 20px;
        font-family: "Afacad", sans-serif;

    }
	#exit {
		font-size: 100px;
		font-weight: bolder;
		text-decoration: none;
		margin-left: 3%;
		margin-top: 3%;
		color: black;
	}

	#team1 {
		font-size: 150%;
		font-weight: bold;
	}

	#team2 {
		font-size: 150%;
		font-weight: bold;
	}

	.editSvg img {
		margin-top: 2%;
		width: 40px;
		height: 40px;
		cursor: pointer;
	}

	h2 {
		font-weight: lighter;
		margin-top: 4%;
	}

	#firstTeam {
		margin-left: 5%;
		margin-top: 1%;
	}

	#secTeam {
		margin-left: 5%;
		margin-top: 5%;
	}

	#playButton {
		width: 35%;
		padding: 4%;
		margin-top: 15%;
		margin-left: 23%;
		font-size: 100%;
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
		cursor: pointer;
        font-family: "Afacad", sans-serif;

    }
</style>
