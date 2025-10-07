<script lang="ts">
	import Header from '../../components/Header.svelte';
	import { navigationState } from '$lib/stores/navigationStore.svelte';

	let teamName = '';
	let players = ['', '', '', ''];
	let isLoading = false;

	async function savePlayers() {
		try {
			isLoading = true;

			//TODO: replace this error handling with highlighting on the input fields
			const nonEmptyPlayers = players.filter((name) => name.trim() !== '');

			if (teamName.trim() === '') {
				throw new Error('Team name is required');
			}

			if (nonEmptyPlayers.length === 0) {
				throw new Error('At least one player is required');
			}

			const teamResponse = await fetch(`/api/team?teamName=${encodeURIComponent(teamName)}`, {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json'
				}
			});

			if (!teamResponse.ok) {
				throw new Error(`Failed to create team: ${teamResponse.status}`);
			}

			const teamDataResponse = await fetch(`/api/team/${encodeURIComponent(teamName)}`);

			if (!teamDataResponse.ok) {
				throw new Error(`Failed to retrieve team: ${teamDataResponse.status}`);
			}

			const team = await teamDataResponse.json();

			await Promise.all(
				nonEmptyPlayers.map(async (name) => {
					await createPlayerIntoTeam(team.teamId, name);
				})
			);
		} catch (err) {
			console.error('Error:', err);
		} finally {
			isLoading = false;
		}

		window.location.href = '/teamSelect';
	}

	async function createPlayerIntoTeam(teamId: number, playerName: string) {
		const playerData = {
			name: playerName,
			team: {
				teamId: teamId
			}
		};

		const createPlayerResponse = await fetch(`/api/players`, {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify(playerData)
		});

		if (!createPlayerResponse.ok) {
			throw new Error(`Failed to create player ${playerName}: ${createPlayerResponse.status}`);
		}

		console.log(`Player ${playerName} created successfully`);
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
				placeholder="Team name"
				class="input-style"
			/>
		</div>

		{#each players as _, i}
			<div class="input-container ic2">
				<label for="player{i + 1}">Player {i + 1}:</label>
				<input
					id="player{i + 1}"
					type="text"
					bind:value={players[i]}
					placeholder={`Spieler ${i + 1} Name`}
					class="input-style"
				/>
			</div>
		{/each}
	</div>

	<a id="nextButton" onclick={savePlayers}>Speichern</a>
</div>

<style>
	* {
		margin: 0;
	}

	.input-style {
		padding: 10px;
		border: 2px solid #ccc;
		border-radius: 10px;
		font-size: 16px;
		color: #555;
		outline: none;
	}

	body {
		align-items: center;
		margin: 0;
		text-align: center;
	}

	#outer_form_box {
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
		margin-top: -10%;
		width: 100%;
	}

	.form {
		font-size: 20px;
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
		font-family: 'Afacad', sans-serif;
	}

	.input-container {
		height: 10%;
		position: relative;
		width: 100%;
	}

	.ic1 {
		margin-top: 8px;
		margin-bottom: 20%;
		font-family: 'Afacad', sans-serif;
	}

	.ic2 {
		margin-top: 40px;
		font-family: 'Afacad', sans-serif;
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
		box-shadow: 2px 2px 2px rgba(0, 0, 0, 0.38);
	}

	#nextButton:hover {
		cursor: pointer;
	}
</style>
