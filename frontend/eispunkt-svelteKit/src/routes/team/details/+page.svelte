<script lang="ts">
	import { onMount } from 'svelte';

	let showModal = $state(false);
	let playerName = $state('');

	onMount(() => {
		getSelectedTeam();
	});

	let players = $state(new Array());

	async function getSelectedTeam() {
		let teamId = localStorage.getItem('teamId');

		const response = await fetch(`https://it200230.cloud.htl-leonding.ac.at/api/players/team/${teamId}`);
		players = await response.json();
		console.log('Players geladen:', players);
	}

	function openModal() {
		if (players.length < 4) {
			showModal = true;
		} else {
			console.log('Too many Players!');
		}
	}

	function closeModal() {
		showModal = false;
	}

	async function savePlayer() {
		if (playerName.trim() === '') {
			alert('Bitte einen Playernamen eingeben!');
			return;
		}

		const playerCreateResponse = await fetch(`https://it200230.cloud.htl-leonding.ac.at/api/players/`, {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify({ name: `${playerName}` })
		});

		const playerResponse = await fetch(`${playerCreateResponse.headers.get('location')}`, {
			method: 'GET'
		});

		let player = await playerResponse.json();

		const addToTeamResposne = await fetch(
			`https://it200230.cloud.htl-leonding.ac.at/api/players/${player.playerId}?teamId=${localStorage.getItem('teamId')}`,
			{
				method: 'PATCH'
			}
		);

		console.log(await addToTeamResposne);
		getSelectedTeam();
		closeModal();
	}

	async function deletePlayer(id: number) {
		const response = await fetch(`https://it200230.cloud.htl-leonding.ac.at/api/players/${id}`, {
			method: 'DELETE'
		});

		if (response.ok) {
			console.log(`Spieler mit ID ${id} wurde gelöscht.`);
			await getSelectedTeam();
		} else {
			console.error('Fehler beim Löschen des Spielers.');
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
						onclick={() => {
							deletePlayer(player.playerId);
						}}
						width="35"
						height="35"
						viewBox="0 0 24 24"
						xmlns="http://www.w3.org/2000/svg"
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
				<h2>Neuen Spieler erstellen</h2>
				<label for="name">Name</label>
				<input id="name" type="text" bind:value={playerName} placeholder="" />
				<button class="save-btn" onclick={savePlayer}>Speichern</button>
			</div>
		</div>
	{/if}

	<div id="addButton" class={players.length < 4 ? '' : 'disabled'} onclick={openModal}>+</div>
</div>

<style>
	@import url('https://fonts.googleapis.com/css2?family=Afacad:ital,wght@0,400..700;1,400..700&family=Protest+Strike&display=swap');

	* {
		margin: 0;
	}
	body {
		width: 100vw;
		height: 100vh;
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
		align-items: center;
		justify-content: center;
	}
	#header-box header {
		color: white;
		font-size: 400%;
		font-weight: bold;
		font-family: 'Afacad', sans-serif;
	}

	#backArrow {
		position: absolute;
		left: 2%;
	}

	#team-details-outer-box {
		width: 100%;
		height: 90%;
		display: flex;
		justify-content: start;
		align-items: center;
		flex-direction: column;
		padding-top: 5%;
	}

	#player-header-box {
		width: 80%;
		height: 10%;
		display: flex;
		justify-content: start;
		align-items: center;
	}

	.team-details {
		width: 80%;
		height: 8%;
		display: flex;
		justify-content: space-between;
		align-items: center;
		padding: 5%;
		margin-top: 5%;
		background-color: #f8f8f8;
		font-size: 12px;
		border-radius: 16px;
		box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
	}

	.svg-box {
		width: 35%;
		display: flex;
		justify-content: center;
		align-items: center;
	}
	.svg-box svg {
		cursor: pointer;
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
		position: relative;
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
		background-color: #f8f8f8;
		border-radius: 16px;
		box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
	}

	.modal h2 {
		font-weight: 600;
		margin-bottom: 10%;
		text-align: center;
	}

	.modal label {
		font-size: 30px;
		text-align: start;
	}

	.modal input {
		width: 90%;
		font-size: 30px;
		margin-bottom: 5%;
		outline: none;
		border: none;
		border-bottom: 2px solid #45caac;
		background-color: #f8f8f8;
	}

	.save-btn {
		width: 50%;
		padding: 5%;
		text-align: center;
		color: white;
		font-size: 20px;
		font-weight: bold;
		background-color: #7fc8ee;
		border: none;
		border-radius: 16px;
		cursor: pointer;
	}

	.close-btn {
		position: absolute;
		top: 0;
		right: 0;
		background: none;
		border: none;
		font-size: 50px;
		cursor: pointer;
		color: #333;
	}

	#addButton {
		width: 8vh;
		height: 8vh;
		display: flex;
		justify-content: center;
		align-items: center;
		position: fixed;
		bottom: 40px;
		right: 40px;
		background-color: #7fc8ee;
		color: white;
		font-size: 40px;
		font-weight: bold;
		border-radius: 16px;
		box-shadow: 0 2px 8px rgba(0, 0, 0, 0.29);
		cursor: pointer;
	}

	@media (min-width: 1024px) {
		svg {
			width: 50px;
			height: 50px;
		}

		.team-details {
			width: 80%;
			height: 8%;
			display: flex;
			justify-content: space-between;
			align-items: center;
			padding: 5%;
			margin-top: 5%;
			background-color: #f8f8f8;
			font-size: 20px;
			border-radius: 16px;
			box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
		}

		#player-header-box h1 {
			font-size: 50px;
		}
	}
</style>
