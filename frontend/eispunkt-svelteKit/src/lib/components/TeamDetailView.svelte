<script lang="ts">
	import { keycloak } from '$lib/keycloak';
	import { user } from '$lib/stores/userStore';

	let { teamId }: { teamId: number } = $props();

	let showModal = $state(false);
	let playerName = $state('');
	let players = $state<any[]>([]);

	let mode = $state<boolean>(false); // false = new, true = existing
	let orphanedPlayers = $state<any[]>([]);
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

	async function getOrphanedPlayers() {
		const response = await fetch(`https://it200230.cloud.htl-leonding.ac.at/api/players/team/0`);
		orphanedPlayers = await response.json();
		console.log(orphanedPlayers);
	}

	async function logout() {
		user.set({ authenticated: false, team: null, role: null });
		await keycloak.logout({ redirectUri: `${window.location.origin}/` });
	}

	function openModal() {
		if (players.length < 4) {
			showModal = true;
			selectedPlayerId = null;
			getOrphanedPlayers();
		}
	}

	function closeModal() {
		showModal = false;
	}

	async function savePlayer() {
		if (!mode) {
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

			const playerResponse = await fetch(playerCreateResponse.headers.get('location')!);
			let player = await playerResponse.json();

			await fetch(
				`https://it200230.cloud.htl-leonding.ac.at/api/players/${player.playerId}?teamId=${teamId}`,
				{
					method: 'PATCH'
				}
			);
		} else {
			if (!selectedPlayerId) {
				alert('Bitte einen Spieler auswählen!');
				return;
			}

			await fetch(
				`https://it200230.cloud.htl-leonding.ac.at/api/players/${selectedPlayerId}?teamId=${teamId}`,
				{
					method: 'PATCH'
				}
			);
		}

		await getPlayers();
		closeModal();
	}

	async function deletePlayer(id: number) {
		const response = await fetch(
			`https://it200230.cloud.htl-leonding.ac.at/api/teams/${teamId}/removePlayer/${id}`,
			{
				method: 'PUT'
			}
		);

		if (response.ok) {
			await getPlayers();
		}
	}
</script>

<div id="body-div">
	<div id="header-box">
		{#if $user.role == 'Team'}
			<a id="backArrow" href="/" title="Zurück zur Startseite">
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
		{:else}
			<a id="backArrow" href="/team" title="Zurück zur Teamübersicht">
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
		{/if}

		<header id="header">Team</header>

		<svg
			height="30px"
			width="30px"
			viewBox="0 0 16 16"
			version="1.1"
			xmlns="http://www.w3.org/2000/svg"
			xmlns:xlink="http://www.w3.org/1999/xlink"
			color="#f8f8f8"
			id="logout"
			onclick={() => logout()}
		>
			<rect width="50" height="50" id="icon-bound" fill="none" />
			<path
				d="M14,14l0,-12l-6,0l0,-2l8,0l0,16l-8,0l0,-2l6,0Zm-9.002,-0.998l-4.998,-5.002l5,-5l1.416,1.416l-2.588,2.584l8.172,0l0,2l-8.172,0l2.586,2.586l-1.416,1.416Z"
				fill="#f8f8f8"
			/>
		</svg>
	</div>

	{#if $user.role == 'Team'}
		<div id="icon-box">
			<div id="team-icon-box">
				<svg
					width="140"
					height="140"
					viewBox="0 0 40 49"
					fill="none"
					xmlns="http://www.w3.org/2000/svg"
				>
					<path
						d="M17.1166 24.5H22.8833C24.4333 24.5 25.5999 22.8054 25.3499 20.9271L24.8499 17.2725C24.4499 14.3733 22.3999 12.25 19.9999 12.25C17.5999 12.25 15.5499 14.3733 15.1499 17.2929L14.6499 20.9475C14.3999 22.8054 15.5666 24.5 17.1166 24.5Z"
						fill="#45CAAC"
					/>
					<path
						d="M2.76671 22.6829C2.55004 23.2137 2.46671 23.8467 2.60004 24.4796C2.86671 25.8883 3.86671 26.5825 5.15004 26.5212C5.15004 26.5212 7.63337 26.5212 8.40004 26.5212C9.78337 26.5212 10.9167 25.3371 10.9167 23.8875C10.9167 23.6017 10.8667 23.3363 10.8 23.0708C10.7834 23.0096 10.7834 22.9688 10.8167 22.9075C10.9667 22.5808 11.05 22.2133 11.05 21.8254C11.05 21.1925 10.8167 20.6004 10.45 20.1512C10.4 20.09 10.4 20.0288 10.4167 19.9471C10.5334 19.5388 10.5334 19.0692 10.4334 18.62C10.1667 17.7421 9.51671 17.15 8.78337 17.1092C8.73337 17.1092 8.70004 17.0888 8.66671 17.0479C8.38337 16.6192 7.8667 16.3333 7.28337 16.3333C6.78337 16.3333 6.33337 16.5375 6.03337 16.8642C5.98337 16.9254 5.93337 16.9254 5.88337 16.905C5.65004 16.7825 5.38337 16.7212 5.11671 16.7212C4.03337 16.7212 3.15004 17.7217 3.05004 19.0079C3.05004 19.0488 3.03337 19.0896 3.00004 19.1304C2.51671 19.6613 2.23337 20.4575 2.31671 21.2742C2.36671 21.7233 2.51671 22.1521 2.73337 22.4992C2.78337 22.54 2.78337 22.6217 2.76671 22.6829Z"
						fill="#45CAAC"
					/>
					<path
						d="M27.0667 27.8687C25.1167 26.8071 22.7167 26.0313 20 26.0313C17.2833 26.0313 14.8833 26.8275 12.9333 27.8687C11.1333 28.8487 10 31.0537 10 33.4629V34.7083C10 35.8313 10.75 36.75 11.6667 36.75H28.3333C29.25 36.75 30 35.8313 30 34.7083V33.4629C30 31.0537 28.8667 28.8487 27.0667 27.8687Z"
						fill="#45CAAC"
					/>
					<path
						d="M2.03333 29.7675C0.8 30.4208 0 31.8908 0 33.5446V34.7083C0 35.8313 0.75 36.75 1.66667 36.75H7.5V33.4629C7.5 31.7683 7.88333 30.1758 8.55 28.7875C7.93333 28.665 7.31667 28.5833 6.66667 28.5833C5.01667 28.5833 3.45 29.0121 2.03333 29.7675Z"
						fill="#45CAAC"
					/>
					<path
						d="M37.9667 29.7675C36.55 29.0121 34.9833 28.5833 33.3333 28.5833C32.6833 28.5833 32.0667 28.665 31.45 28.7875C32.1167 30.1758 32.5 31.7683 32.5 33.4629V36.75H38.3333C39.25 36.75 40 35.8313 40 34.7083V33.5446C40 31.8908 39.2 30.4208 37.9667 29.7675Z"
						fill="#45CAAC"
					/>
					<path
						d="M36.6667 22.4583V21.4375C36.6667 19.1917 35.1667 17.3542 33.3334 17.3542H30C29.3 17.3542 28.9167 18.3342 29.35 19.0079L30.5167 20.2942C30.2 20.9271 30 21.6621 30 22.4583C30 24.7042 31.5 26.5417 33.3334 26.5417C35.1667 26.5417 36.6667 24.7042 36.6667 22.4583Z"
						fill="#45CAAC"
					/>
				</svg>
			</div>

			<a id="tournament-icon-box" href="/tournament" title="Zu den Turnieren">
				<svg
					width="140"
					height="140"
					viewBox="0 0 24 24"
					fill="none"
					xmlns="http://www.w3.org/2000/svg"
				>
					<path
						d="M19 5H17V4C17 3.45 16.55 3 16 3H8C7.45 3 7 3.45 7 4V5H5C3.9 5 3 5.9 3 7V8C3 10.55 4.92 12.63 7.39 12.94C8.02 14.44 9.37 15.57 11 15.9V19H8C7.45 19 7 19.45 7 20C7 20.55 7.45 21 8 21H16C16.55 21 17 20.55 17 20C17 19.45 16.55 19 16 19H13V15.9C14.63 15.57 15.98 14.44 16.61 12.94C19.08 12.63 21 10.55 21 8V7C21 5.9 20.1 5 19 5ZM5 8V7H7V10.82C5.84 10.4 5 9.3 5 8ZM19 8C19 9.3 18.16 10.4 17 10.82V7H19V8Z"
						fill="#45CAAC"
					/>
				</svg>
			</a>
		</div>
	{/if}

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
					<label class="switchLabel">Neu</label>
					<label class="switch">
						<input type="checkbox" bind:checked={mode} />
						<span class="slider round"></span>
					</label>
					<label class="switchLabel">Bestehend</label>
				</div>

				{#if !mode}
					<label>Name</label>
					<input type="text" bind:value={playerName} />
				{:else}
					<label>Spieler auswählen</label>
					<select bind:value={selectedPlayerId}>
						<option value={null}>Bitte wählen</option>
						{#each orphanedPlayers as p}
							{#if !players.some((tp) => tp.playerId === p.playerId)}
								<option value={p.playerId}>{p.name}</option>
							{/if}
						{/each}
					</select>
				{/if}

				<button class="save-btn" onclick={savePlayer}>Speichern</button>
			</div>
		</div>
	{/if}

	<div id="addButton" class={players.length < 4 ? '' : 'disabled'} onclick={openModal}>+</div>
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

	#header {
		font-size: 400%;
		font-weight: bold;
		color: #f8f8f8;
	}

	#backArrow {
		position: absolute;
		left: 2%;
	}

	#logout {
		position: absolute;
		right: 5%;
		margin-top: -1%;
		cursor: pointer;
	}

	#team-details-outer-box {
		height: 90%;
		display: flex;
		flex-direction: column;
		align-items: center;
		padding-top: 5%;
	}

	#icon-box {
		width: 100%;
		height: 8%;
		display: flex;
		justify-content: center;
		align-items: center;
	}

	#team-icon-box {
		width: 50%;
		height: 100%;
		display: flex;
		justify-content: center;
		align-items: center;
		border-top-left-radius: 20px;
		border-top-right-radius: 20px;
		border-bottom: 4px solid #45caac;
		background-color: rgba(128, 128, 128, 0.06);
	}
	#team-icon-box svg {
		height: 105%;
		width: 105%;
	}

	#tournament-icon-box {
		width: 50%;
		height: 100%;
		display: flex;
		justify-content: center;
		align-items: center;
	}
	#tournament-icon-box svg {
		height: 75%;
		width: 75%;
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
		width: 100%;
		display: flex;
		justify-content: space-evenly;
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

	.switch {
		position: relative;
		display: inline-block;
		width: 20%;
		height: 34px;
	}

	.switchLabel{
		width:20%;
		text-align: center;
	}

	.switch input {
		opacity: 0;
		width: 0;
		height: 0;
	}

	.slider {
		position: absolute;
		cursor: pointer;
		top: 0;
		left: 0;
		right: 0;
		bottom: 0;
		background-color: #ccc;
		-webkit-transition: 0.4s;
		transition: 0.4s;
	}

	.slider:before {
		position: absolute;
		content: '';
		height: 26px;
		width: 26px;
		left: 4px;
		bottom: 4px;
		background-color: white;
		-webkit-transition: 0.4s;
		transition: 0.4s;
	}

	input:checked + .slider {
		background-color: #7fc8ee;
	}

	input:focus + .slider {
		box-shadow: 0 0 1px #7fc8ee;
	}

	input:checked + .slider:before {
		-webkit-transform: translateX(26px);
		-ms-transform: translateX(26px);
		transform: translateX(26px);
	}

	.slider.round {
		border-radius: 34px;
	}

	.slider.round:before {
		border-radius: 50%;
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
