<script lang="ts">
	import { selectedGame, selectedTeams, selectedTournament } from '$lib/stores/selectionStore';
	import { onMount } from 'svelte';

	let allRounds = $state(new Array());
	let currentRoundIndex = $state(0);

	const currentGames = $derived(allRounds[currentRoundIndex] || []);

	$inspect(allRounds)
	$inspect(currentRoundIndex)

	onMount(async () => {
		let gamesResponse = await fetch(
			`https://it200230.cloud.htl-leonding.ac.at/api/tournaments/generate/${$selectedTournament}`,
			{ method: 'PATCH' }
		);

		allRounds = await gamesResponse.json();
	});

	function navigateToGame(team1Id: number, team2Id: number, gameId: number) {
		selectedTeams.set({ selectedTeams: [team1Id, team2Id] });
		selectedGame.set({ selectedGame: gameId });
		window.location.href = '/game';
	}

	function goToPreviousRound() {
		if (currentRoundIndex > 0) {
			currentRoundIndex--;
		}
	}

	function goToNextRound() {
		if (currentRoundIndex < allRounds.length - 1) {
			currentRoundIndex++;
		}
	}
</script>

<div id="body-div">
	<div id="header-box">
		<a id="backArrow" href="/tournament">
			<svg xmlns="http://www.w3.org/2000/svg" width="100" height="100" viewBox="0 0 24 24">
				<path fill="none" stroke="white" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 12h14M5 12l6 6m-6-6l6-6"/>
			</svg>
		</a>

		<header id="header">Turnier - Runde {currentRoundIndex + 1}/{allRounds.length}</header>
	</div>

	<div id="displayBox">
		{#each currentGames as game}
		<div class='turnierDet'>
			<div>
				{#if game.team2}
					<h1>{game.team1?.name}</h1>
					<h1>{game.team2?.name}</h1>
				{:else}
					<h1>{game.team1?.name} spielt nicht!</h1>
				{/if}
			</div>
			{#if game.team2}
			<a on:click={() => {navigateToGame(game.team1.teamId, game.team2.teamId, game.gameId)}}>
				<svg width="100" height="100" viewBox="0 0 41 36" fill="none" xmlns="http://www.w3.org/2000/svg">
					<path d="M13.6667 10.23V25.77C13.6667 26.955 15.153 27.675 16.2976 27.03L30.2034 19.26C31.2626 18.675 31.2626 17.325 30.2034 16.725L16.2976 8.97001C15.153 8.32501 13.6667 9.04501 13.6667 10.23Z" fill="#7FC8EE"/>
				</svg>
			</a>
			{/if}
		</div>
		{/each}
	</div>

	<div id="navigation-box">
		<button on:click={goToPreviousRound} disabled={currentRoundIndex === 0}>← Vorherige</button>
		<button on:click={goToNextRound} disabled={currentRoundIndex === allRounds.length - 1}>Nächste →</button>
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
		overflow: hidden;
		font-family: 'Afacad', sans-serif;
	}

	#header-box {
		width: 100%;
		height: 10%;
		display: flex;
		background-color: #45caac;
		text-align: center;
		align-items: center;
		justify-content: center;
	}

	#displayBox {
		overflow: scroll;
		flex: 1;
	}

	#navigation-box {
		display: flex;
		justify-content: center;
		gap: 15vw;
		padding: 20px;
		background-color: #f8f8f8;
		border-top: 1px solid #ddd;
	}

	#navigation-box button {
		padding: 10px 20px;
		font-size: 16px;
		font-family: 'Afacad', sans-serif;
		background-color: #45caac;
		color: white;
		border: none;
		border-radius: 8px;
		cursor: pointer;
		font-weight: bold;
		transition: background-color 0.3s;
	}

	#navigation-box button:hover:not(:disabled) {
		background-color: #2eb8a0;
	}

	#navigation-box button:disabled {
		background-color: #ccc;
		cursor: not-allowed;
	}

	#header {
		color: white;
		font-size: 215%;
		margin-left:15%;
		font-weight: bold;
		font-family: 'Afacad', sans-serif;
	}

	#backArrow {
		position: absolute;
		left: 2%;
	}
	
	a svg{
		height:10vh;
		width:10vh;
	}

	svg{
		cursor: pointer;
	}

	.turnierDet {
		display: flex;
		justify-content: space-between;
		width: 60%;
		background-color: #f8f8f8;
		padding: 8%;
		border-radius: 16px;
		box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
		font-size: 10px;
		margin: 12%;
		margin-top: 14%;
		font-family: 'Afacad', sans-serif;
	}

	.turnierDet a {
		margin-left: 20%;
		margin-top: 2%;
	}
</style>
