<script lang="ts">
	import { onMount } from 'svelte';
	import { gameState } from '$lib/stores/gameStore.svelte.js';
	import { selectionState } from '$lib/stores/selectionStore.svelte.js';
	import Header from '../../../components/Header.svelte';

	onMount(async () => {
		console.log($selectionState.selectedTeam1Name);
		await createStage();
		await createTurn();
	});

	async function createStage() {
		try {
			if (gameState.gameId === 0) {
				return;
			}

			const response = await fetch(
				`/api/stages/create?gameId=${gameState.gameId}&stageNumber=${gameState.stageNumber}`,
				{
					method: 'POST'
				}
			);

			if (!response.ok) {
				throw new Error(`Failed to create stage: ${response.status}`);
			}

			const stage = await response.json();
			gameState.currentStageId = stage.stageId;
		} catch (error) {
			console.error('Error creating stage:', error);
		}
	}

	async function createTurn() {
		try {
			if (gameState.currentStageId === 0) {
				return;
			}

			const response = await fetch(`/api/turns/create?stageId=${gameState.currentStageId}`, {
				method: 'POST'
			});

			if (!response.ok) {
				throw new Error(`Failed to create turn: ${response.status}`);
			}

			const turn = await response.json();
			gameState.currentTurnId = turn.turnId;
		} catch (error) {
			console.error('Error creating turn:', error);
		}
	}

	async function saveScore(teamId: number, points: number) {
		try {
			if (gameState.currentTurnId === 0) {
				return;
			}

			const response = await fetch(
				`/api/scores/create?teamId=${teamId}&turnId=${gameState.currentTurnId}&score=${points}`,
				{
					method: 'POST'
				}
			);

			if (!response.ok) {
				throw new Error(`Failed to save score: ${response.status}`);
			}
		} catch (error) {
			console.error('Error saving score:', error);
		}
	}

	function checkWinner() {
		if (gameState.team1Score >= 12) {
			gameState.winner = 1;
			gameState.gameFinished = true;
		} else if (gameState.team2Score >= 12) {
			gameState.winner = 2;
			gameState.gameFinished = true;
		}
	}

	async function completeTurn() {
		if (gameState.isProcessingTurn) return;
		gameState.isProcessingTurn = true;

		try {
			await saveScore($selectionState.selectedTeam1, gameState.team1SubScore);
			await saveScore($selectionState.selectedTeam2, gameState.team2SubScore);
			if (gameState.team1SubScore > gameState.team2SubScore) {
				gameState.team1Score += 2;
				gameState.turnHistory.push('team1');
			} else if (gameState.team2SubScore > gameState.team1SubScore) {
				gameState.team2Score += 2;
				gameState.turnHistory.push('team2');
			} else if (gameState.team2SubScore == gameState.team1SubScore) {
				gameState.team1Score += 1;
				gameState.team2Score += 1;
				gameState.turnHistory.push('draw');
			}

			checkWinner();

			gameState.team1SubScore = 0;
			gameState.team2SubScore = 0;
			gameState.totalPointsThisTurn = 0;
			gameState.turnNumber += 1;

			if (!gameState.gameFinished) {
				await createTurn();
			}
		} catch (error) {
			console.error('Error completing turn:', error);
		} finally {
			gameState.isProcessingTurn = false;
		}
	}

	function addPoint(team: number) {
		if (gameState.totalPointsThisTurn >= 4 || gameState.isProcessingTurn || gameState.gameFinished)
			return;

		if (team === 1) {
			gameState.team1SubScore++;
		} else if (team === 2) {
			gameState.team2SubScore++;
		}

		gameState.totalPointsThisTurn++;

		if (
			gameState.team1SubScore === 3 ||
			gameState.team2SubScore === 3 ||
			gameState.totalPointsThisTurn === 4
		) {
			completeTurn();
		}
	}

	function deletePoint(team: number) {
		if (gameState.isProcessingTurn || gameState.gameFinished) return;

		if (team === 1 && gameState.team1SubScore > 0) {
			gameState.team1SubScore--;
			gameState.totalPointsThisTurn--;
		} else if (team === 2 && gameState.team2SubScore > 0) {
			gameState.team2SubScore--;
			gameState.totalPointsThisTurn--;
		}
	}

	const headerText = $derived(
		gameState.gameFinished
			? `${gameState.winner === 1 ? $selectionState.selectedTeam1Name : $selectionState.selectedTeam2Name} Wins!`
			: 'Eispunkt'
	);
</script>

<Header {headerText}></Header>
<div id="scoreHeader">
	<div id="progressBar">
		{#each Array(8) as _, index}
			<div
				class="progress-segment"
				class:team1={gameState.turnHistory[index] === 'team1'}
				class:team2={gameState.turnHistory[index] === 'team2'}
				class:draw={gameState.turnHistory[index] === 'draw'}
			></div>
		{/each}
	</div>
</div>
<div class="team_outer_box">
	<div class="team_inner_box">
		<h1>{$selectionState.selectedTeam1Name}</h1>
	</div>
	<div class="points_buttons_outer_box">
		<div onclick={() => deletePoint(1)} class="points_button">-</div>
		<div id="t1SubScoreText">{gameState.team1SubScore}</div>
		<div onclick={() => addPoint(1)} class="points_button">+</div>
	</div>
</div>
<div class="team_outer_box">
	<div class="team_inner_box">
		<h1>{$selectionState.selectedTeam2Name}</h1>
	</div>
	<div class="points_buttons_outer_box">
		<div onclick={() => deletePoint(2)} class="points_button">-</div>
		<div id="t2SubScoreText">{gameState.team2SubScore}</div>
		<div onclick={() => addPoint(2)} class="points_button">+</div>
	</div>
</div>

<style>
	* {
		margin: 0;
		font-family: Afacad;
	}

	body {
		width: 100%;
		height: 100%;
		justify-content: center;
		align-items: center;
	}

	.afacad-afacad {
		font-family: 'Afacad', sans-serif;
		font-optical-sizing: auto;
		font-weight: 400;
		font-style: normal;
	}

	#progressBar {
		display: flex;
		gap: 4px;
		margin-top: 10px;
		width: 100%;
		height: 100%;
	}

	.progress-segment {
		flex: 1;
		height: 100%;
		background-color: #ccc;
		border-radius: 4px;
		transition: background-color 0.3s ease;
	}

	.progress-segment.team1 {
		background-color: #dc3545; /* Red for team 1 */
	}

	.progress-segment.team2 {
		background-color: #007bff; /* Blue for team 2 */
	}

	.progress-segment.draw {
		background-color: #28a745; /* Green for draw */
	}

	#scoreHeader {
		height: 4vh;
		display: flex;
		flex-direction: row;
		justify-content: space-evenly;
		padding-top: 1vh;
	}

	#scoreHeader h2 {
		width: 20vw;
		font-size: 50px;
		text-align: center;
	}

	#game {
		height: 10vh;
		background-color: #45caac;
		text-align: center;
		color: white;
		font-weight: bold;
		font-size: 400%;
		font-family: 'Protest Strike', serif;
	}

	.team_outer_box {
		padding: 2%;
		height: 30vh;
		display: flex;
		flex-direction: column;
		justify-content: start;
		margin-top: 5%;
	}

	.team_inner_box {
		width: 100%;
		height: 30%;
		justify-content: left;
	}
	.team_inner_box > h1 {
		font-size: 200%;
		text-align: center;
		margin-bottom: 5%;
	}

	#t1SubScoreText {
		font-size: 750%;
		font-family: 'Afacad', sans-serif;
	}

	#t2SubScoreText {
		font-size: 750%;
		font-family: 'Afacad', sans-serif;
	}

	.points_buttons_outer_box {
		width: 100%;
		height: 60%;
		margin-top: 10%;
		display: flex;
		flex-direction: row;
		justify-content: space-evenly;
		align-items: center;
	}

	.points_button {
		color: white;
		width: 10vh;
		height: 10vh;
		display: flex;
		justify-content: center;
		align-items: center;
		background-color: #7fc8ee;
		font-size: 1000%;
		border-radius: 20%;
		box-shadow: 4px 15px 6px rgba(127, 200, 238, 0.5);
		transition:
			box-shadow 0.3s,
			transform 0.3s;
		cursor: pointer;
	}
</style>
