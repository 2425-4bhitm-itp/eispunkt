<script lang="ts">
	import { type Team } from '$lib/interfaces/team';
	import { selectedGame } from '$lib/stores/selectionStore';
	import { onMount } from 'svelte';

	let stageNumber = $state(1);
	let stageId = 0;
	let turnNumber = $state(1);

	let teams = $state(new Array<Team>());

	let t1Score = $state(0);
	let t2Score = $state(0);
	let t1SuperScore = $state(0);
	let t2SuperScore = $state(0);

	let t1StageScore = 0;
	let t2StageScore = 0;

	let currentSegment = 0;
	let progressSegments: any;

	let overlayVisible = $state(false);
	let winnerText = $state('');


	onMount(() => {
		startNewStage();
		getGameSummary();
		progressSegments = document.getElementsByClassName('progress-segment');
		console.log(progressSegments);
	});

	async function startNewStage() {
		t1StageScore = 0;
		t2StageScore = 0;
		let res = await fetch('https://it200230.cloud.htl-leonding.ac.at/api/stages/', {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify({
				gameId: $selectedGame.selectedGame,
				stageNumber: stageNumber
			})
		});
		let stageObject = await res.json();
		stageId = stageObject.stageId;
	}

	async function getGameSummary() {
		let res = await fetch(`https://it200230.cloud.htl-leonding.ac.at/api/games/${$selectedGame.selectedGame}`);
		let gameSummary = await res.json();
		teams = gameSummary.teams;
	}

	async function submitScore() {
		let res = await fetch('https://it200230.cloud.htl-leonding.ac.at/api/turns/', {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify({
				stageId: stageId,
				turnNumber: turnNumber,
				teamId: teams[t1Score > 0 ? 0 : 1].teamId,
				score: t1Score > 0 ? t1Score : t2Score
			})
		});

		progressSegments[currentSegment].classList.add(t1Score > 0 ? 'team1' : 'team2');
		progressSegments[currentSegment].innerHTML = t1Score > 0 ? `${t1Score}` : `${t2Score}`;

		if (t1Score > t2Score) {
			t1StageScore += t1Score;
		} else {
			t2StageScore += t2Score;
		}

		let turnObject = await res.json();
		console.log(turnObject);
		turnNumber++;
		currentSegment++;

		if (turnNumber > 6) {
			turnNumber = 1;
			stageNumber++;

			if (t1StageScore > t2StageScore) {
				t1SuperScore += 2;
			} else if (t2StageScore > t1StageScore) {
				t2SuperScore += 2;
			} else {
				t1SuperScore += 1;
				t2SuperScore += 1;
			}


			if (stageNumber > 3) {
				checkWin();
				return;
			}

			for (let i = 0; i < progressSegments.length; i++) {
				if (progressSegments[i].classList.contains('team1')) {
					progressSegments[i].classList.remove('team1');
				} else if (progressSegments[i].classList.contains('team2')) {
					progressSegments[i].classList.remove('team2');
				}
				progressSegments[i].innerHTML = '';
			}

			currentSegment = 0;

			await startNewStage();
		}

		resetScore();
	}

	function resetScore() {
		t1Score = 0;
		t2Score = 0;
	}

	function checkWin() {
		if (t1SuperScore > t2SuperScore) {
			winnerText = `${teams[0].name} gewinnt!`;
		} else if (t2SuperScore > t1SuperScore) {
			winnerText = `${teams[1].name} gewinnt!`;
		} else {
			winnerText = `Gleichstand!`;
		}
		toggleModal();
	}

	/*
	function resetGame() {
		resetSub();
		currentSegment = 0;
		for (let i = 0; i < progressSegments.length; i++) {
			progressSegments[i].classList.remove('team1', 'team2', 'draw');
		}
		closeModal();
		updateText();
	}
*/

	function toggleModal() {
		overlayVisible = !overlayVisible;
	}
</script>

<div id="noZoom">
	<div id="game">
		<h1 id="superScoreCounter">{t1SuperScore} - {t2SuperScore}</h1>
	</div>
	<div id="progressBar">
		<div class="progress-segment"></div>
		<div class="progress-segment"></div>
		<div class="progress-segment"></div>
		<div class="progress-segment"></div>
		<div class="progress-segment"></div>
		<div class="progress-segment"></div>
	</div>
	<div class="team_outer_box">
		<div class="team_inner_box">
			<div class="team_indicator team1"></div>
			{#if teams.length == 0}
				<h1>Loading...</h1>
			{:else}
				<h1>{teams[0].name}</h1>
			{/if}
		</div>
		<div class="points_buttons_outer_box">
			{#if t2Score == 0}
				<div
					on:click={() => {
						if (t1Score > 0) {
							t1Score--;
						}
					}}
					class="points_button"
				>
					<p class="minus">-</p>
				</div>
				<h2 id="t1ScoreText">{t1Score}</h2>
				<div
					on:click={() => {
						if (t1Score < 4) {
							t1Score++;
						} else {
						}
					}}
					class="points_button"
				>
					<p>+</p>
				</div>
			{:else}
				<div class="points_button disabled">
					<p class="minus">-</p>
				</div>
				<h2 id="t1ScoreText">{t1Score}</h2>
				<div class="points_button disabled">
					<p>+</p>
				</div>
			{/if}
		</div>
	</div>
	<div class="team_outer_box">
		<div class="team_inner_box">
			<div class="team_indicator team2"></div>
			{#if teams.length == 0}
				<h1>Loading...</h1>
			{:else}
				<h1>{teams[1].name}</h1>
			{/if}
		</div>
		<div class="points_buttons_outer_box">
			{#if t1Score == 0}
				<div
					on:click={() => {
						if (t2Score > 0) {
							t2Score--;
						}
					}}
					class="points_button"
				>
					<p class="minus">-</p>
				</div>

				<h2 id="t2ScoreText">{t2Score}</h2>
				<div
					on:click={() => {
						if (t2Score < 4) {
							t2Score++;
						}
					}}
					class="points_button"
				>
					<p>+</p>
				</div>
			{:else}
				<div class="points_button disabled">
					<p class="minus">-</p>
				</div>

				<h2 id="t2ScoreText">{t2Score}</h2>
				<div class="points_button disabled">
					<p>+</p>
				</div>
			{/if}
		</div>
	</div>

	{#if t1Score > 0 || t2Score > 0}
		<div class="submit_box">
			<button class="save-btn" on:click={submitScore}>Submit</button>
		</div>
	{/if}

	{#if overlayVisible}
		<div class="overlay" id="overlay">
			<div class="modal">
				<h2 id="winnerText">{winnerText}</h2>

				<button class="save-btn">
					<a href="/">Home</a>
				</button>
			</div>
		</div>
	{/if}
</div>

<style>
	@import url('https://fonts.googleapis.com/css2?family=Afacad:ital,wght@0,400..700;1,400..700&family=Protest+Strike&display=swap');
	@import url('https://fonts.googleapis.com/css2?family=Protest+Strike&display=swap');

	#noZoom {
		touch-action: manipulation;
	}

	* {
		margin: 0;
		font-family: Afacad;
	}

	.disabled {
		background-color: #ccc !important;
		cursor: default !important;
	}

	body {
		touch-action: manipulation;
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

	#scoreHeader {
		height: 4vh;
		border-bottom: 5px solid #7fc8ee;
		display: flex;
		flex-direction: row;
		justify-content: space-evenly;
		padding-top: 1vh;
	}

	#scoreHeader h2 {
		width: 20vw;
		font-size: 45px;
		text-align: center;
	}

	#game {
		height: 10vh;
		background-color: #45caac;
		text-align: center;
		color: white;
		font-weight: bold;
		font-size: 210%;
		font-family: 'Protest Strike', serif;
	}

	.team_outer_box {
		padding: 2%;
		height: 30vh;
		display: flex;
		flex-direction: column;
		justify-content: center;
		margin-top: 5%;
		align-items: center;
	}

	.team_inner_box {
		display: flex;
		flex-direction: row;
		width: 85vw;
		height: 30%;
		text-align: center;
	}

	.team_indicator {
		width: 5px;
		height: 100%;
		margin-right: 2%;
	}

	.team_inner_box > h1 {
		width: 98%;
		font-size: clamp(2rem, 4vw, 3rem);
	}

	.points_buttons_outer_box {
		background-color: rgba(127, 200, 238, 0.33);
		border-radius: 20px;
		width: 85vw;
		display: flex;
		flex-direction: row;
		justify-content: center;
		align-items: center;
		margin-top: 2vh;
	}

	.points_buttons_outer_box h2 {
		font-family: 'Protest Strike', sans-serif;
		font-size: 90px;
		font-weight: 400;
		color: white;
		padding: 0 10vw;
		user-select: none;
		text-align: center;
	}

	.points_button {
		text-align: center;
		color: white;
		width: 19vh;
		height: 12vh;
		display: flex;
		justify-content: center;
		align-items: center;
		background-color: #7fc8ee;
		font-size: 800%;
		border-radius: 20%;
		box-shadow: 4px 3px 6px rgba(127, 200, 238, 0.5);
		transition:
			box-shadow 0.3s,
			transform 0.3s;
		cursor: pointer;
	}

	.points_button p {
		user-select: none;
		text-align: center;
	}

	.minus {
		transform: translateY(-15px);
	}
	#progressBar {
		display: flex;
		gap: 4px;
		margin-top: 10px;
		width: 100%;
		height: 5vh;
	}

	.progress-segment {
		flex: 1;
		height: 100%;
		background-color: #ccc;
		border-radius: 4px;
		transition: background-color 0.3s ease;
		color: black;
		justify-content: center;
		text-align: center;
		font-weight: bold;
		font-size: 200%;
	}

	:global(.team1) {
		background-color: #dc3545 !important;
	}
	:global(.team2) {
		background-color: #007bff !important;
	}
	:global(.draw) {
		background-color: #28a745 !important;
	}

	.next_button_box {
		width: 100%;
		margin-top: 20%;
		display: flex;
		justify-content: center;
	}

	:global(.overlay) {
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
		position: relative;
		display: flex;
		flex-direction: column;
		background-color: #f8f8f8;
		border-radius: 16px;
		box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
		padding: 10%;
		width: 50%;
		height: 30%;
		animation: fadeIn 0.2s ease-out;
		justify-content: center;
		align-content: center;
		margin: 5% 5% 25%;
		font-family: 'Afacad', sans-serif;
	}

	.modal h2 {
		font-size: 20px !important;
		font-weight: 600;
		margin: 0 0;
		text-align: center;
	}

	.modal a {
		color: #f8f8f8;
		text-decoration: none;
	}

	.save-btn {
		width: 80%;
		background-color: #7fc8ee;
		color: white;
		border: none;
		padding: 4%;
		font-size: 30px;
		cursor: pointer;
		font-weight: bold;
		border-radius: 16px;
		margin-bottom: 10%;
		margin-left: 10%;
		font-family: 'Afacad', sans-serif;
		text-align: center;
		justify-content: center;
	}

	.close-btn {
		position: absolute;
		top: 20px;
		right: 30px;
		background: none;
		border: none;
		font-size: 80px;
		cursor: pointer;
		color: #333;
	}

	@keyframes fadeIn {
		from {
			opacity: 0;
			transform: scale(0.97);
		}
		to {
			opacity: 1;
			transform: scale(1);
		}
	}

	#winnerText {
		font-size: 50rem;
		font-weight: bold;
		text-align: center;
		margin-bottom: 10%;
	}
</style>
