<script lang="ts">
	import { selectedTeams, selectedTournament } from '$lib/stores/selectionStore';
	import { onMount } from 'svelte';

	let games = $state(new Array());
	let teams = $state(new Array());

	$inspect(games)
	$inspect(teams)

	function parseGames(gameStrings: string[]) {
    	return gameStrings.map(g => {
        	const [team1, team2] = g.split(" vs ").map(Number);
        	return { team1, team2 };
    	});
	}

	onMount(async () => {
		let gamesResponse = await fetch(
			`https://it200230.cloud.htl-leonding.ac.at/api/tournaments/generate/${$selectedTournament}`,
			{ method: 'PATCH' }
		);

		games = parseGames(await gamesResponse.json());

		let teamsResponse = await fetch(
			`https://it200230.cloud.htl-leonding.ac.at/api/tournaments/team/${$selectedTournament}`
		)

		teams = await teamsResponse.json()
	});

	function navigateToGame(team1Id:number, team2Id:number){
		selectedTeams.set([team1Id, team2Id])
		window.location.href = "/game"
	}
</script>

<div id="body-div">
	<div id="header-box">
		<a id="backArrow" href="/tournament">
			<svg xmlns="http://www.w3.org/2000/svg" width="100" height="100" viewBox="0 0 24 24">
				<path fill="none" stroke="white" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 12h14M5 12l6 6m-6-6l6-6"/>
			</svg>
		</a>

		<header id="header">Turnier</header>
	</div>

	<div id="displayBox">
		{#each games as game}
		<div class='turnierDet'>
			<div>
				<h1>{teams.at(game.team1)}</h1>
				<h1>{teams.at(game.team2)}</h1>
			</div>
			<a onclick={() => {navigateToGame(game.team1, game.team2)}}>
				<svg width="100" height="100" viewBox="0 0 41 36" fill="none" xmlns="http://www.w3.org/2000/svg">
					<path d="M13.6667 10.23V25.77C13.6667 26.955 15.153 27.675 16.2976 27.03L30.2034 19.26C31.2626 18.675 31.2626 17.325 30.2034 16.725L16.2976 8.97001C15.153 8.32501 13.6667 9.04501 13.6667 10.23Z" fill="#7FC8EE"/>
				</svg>
			</a>
		</div>
		{/each}
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

	#displayBox{
				overflow: scroll;
	}

	#header {
		color: white;
		font-size: 300%;
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
