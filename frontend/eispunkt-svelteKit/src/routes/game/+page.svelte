<script lang="ts">
	import { browser } from '$app/environment';
	import { onMount } from 'svelte';

	let t1SubScore = 0;
	let t1SuperScore = $state(0);
	let t2SubScore = 0;
	let t2SuperScore = $state(0);
	let currentSegment = 0;

	let overlay: HTMLElement | null = null;

	onMount(() => {
		overlay = document.getElementById('overlay');
	});

	console.log('(POST) /api/stage/{stage} - Stage Objekt  im request body');
	console.log(`insert into Stage (stagenumber, game_gameid)
                 values ({stage.stagenumber}, {stage.gameId});`);
	console.log(`(POST) \`/api/turn/{turn}\` - Turn Objekt mit im request body`);
	console.log(`insert into Turn (stage_stageid) values ({turn.stage_stageid});`);
	console.log('(POST) /api/score/{score} - Score Objekt im request body');
	console.log(`insert into Score (score, team_teamid, turn_turnid) values ({score.score},
            {score.turn_turnid}, {score.game_gameId});`);

	console.log('(GET) `/api/game/{gameId}/summary`');
	console.log(`select t.teamid, t.name, s.stageid, tu.turnid, sc.scoreid, sc.score
             from GAME g
                      join public.game_team gt on g.gameid = gt.games_gameid
                      join public.team t on t.teamid = gt.teams_teamid
                      join public.stage s on g.gameid = s.game_gameid
                      join turn tu on s.stageid = tu.stage_stageid
                      join score sc on tu.turnid = sc.turn_turnid
             where g.gameid = 2
             group by t.teamid, s.stageid, tu.turnid, sc.scoreid;`);

	let progressSegments: any;

	progressSegments = document.getElementsByClassName('progress-segment');

	function addPoint(index: number) {
		console.log('(PUT) `/api/scores/{score}` - Score Objekt mit neuem Punktestand im request body');
		console.log(`UPDATE public.score
                 SET score       = score.score,
                     team_teamid = score.team,
                     turn_turnid = score.turn
                 WHERE scoreid = score.scoreId;`);

		if (index == 1) {
			t1SubScore++;
			updateText();
			if (t1SubScore > 2) {
				t1SuperScore += 2;
				progressSegments[currentSegment].classList.add('team1');
				checkWin();
				resetSub();
			} else if (t1SubScore == 2 && t2SubScore == 2) {
				t1SuperScore += 1;
				t2SuperScore += 1;
				progressSegments[currentSegment].classList.add('draw');
				checkWin();
				resetSub();
			}
		} else {
			t2SubScore++;
			updateText();
			if (t2SubScore > 2) {
				t2SuperScore += 2;
				progressSegments[currentSegment].classList.add('team2');
				checkWin();
				resetSub();
			} else if (t2SubScore == 2 && t1SubScore == 2) {
				t2SuperScore += 1;
				t1SuperScore += 1;
				progressSegments[currentSegment].classList.add('draw');
				checkWin();
				resetSub();
			}
		}
	}

	function deletePoint(teamId: number) {
		console.log('(PUT) /api/scores/{score} - Score Objekt mit neuem Punktestand im request body');
		console.log(`UPDATE public.score
                 SET score       = score.score,
                     team_teamid = score.team,
                     turn_turnid = score.turn
                 WHERE scoreid = score.scoreId;`);

		if (teamId == 1 && t1SubScore > 0) {
			t1SubScore--;
		} else {
			if (t2SubScore > 0) {
				t2SubScore--;
			}
		}
		updateText();
	}

	function resetSub() {
		console.log('(POST) /api/stage/{stage} - Stage Objekt  im request body');
		console.log(`insert into Stage (stagenumber, game_gameid)
                 values ({stage.stagenumber}, {stage.gameId});`);
		console.log(`(POST) \`/api/turn/{turn}\` - Turn Objekt mit im request body`);
		console.log(`insert into Turn (stage_stageid) values ({turn.stage_stageid});`);
		console.log('(POST) /api/score/{score} - Score Objekt im request body');
		console.log(`insert into Score (score, team_teamid, turn_turnid) values ({score.score},
            {score.turn_turnid}, {score.game_gameId});`);

		t1SubScore = 0;
		t2SubScore = 0;
		currentSegment++;
		updateText();
	}

	function updateText() {
		if (browser) {
			document.getElementById('t1SubScoreText')!.innerText = `${t1SubScore}`;
			document.getElementById('t2SubScoreText')!.innerText = `${t2SubScore}`;
		}
	}

	function checkWin() {
		if (t1SuperScore >= 12) {
			document.getElementById('winnerText')!.innerHTML = `<h2>Eispunkt Wins</h2>`;
			openModal();
		} else if (t2SuperScore >= 12) {
			document.getElementById('winnerText')!.innerHTML = `<h2>Eisbär Wins</h2>`;
			openModal();
		} else {
			return;
		}
	}

	function resetGame() {
		resetSub();
		currentSegment = 0;
		t1SuperScore = 0;
		t2SuperScore = 0;
		for (let i = 0; i < progressSegments.length; i++) {
			progressSegments[i].classList.remove('team1', 'team2', 'draw');
		}
		closeModal();
		updateText();
	}

	function openModal() {
		overlay?.classList.add('active');
	}

	function closeModal() {
		overlay?.classList.remove('active');
	}
</script>

<div id="noZoom">
	<div id="game">
		<h1 id="superScoreCounter">{t1SuperScore}-{t2SuperScore}</h1>
	</div>
	<div id="progressBar">
		<div class="progress-segment"></div>
		<div class="progress-segment"></div>
		<div class="progress-segment"></div>
		<div class="progress-segment"></div>
		<div class="progress-segment"></div>
		<div class="progress-segment"></div>
		<div class="progress-segment"></div>
		<div class="progress-segment"></div>
		<div class="progress-segment"></div>
		<div class="progress-segment"></div>
		<div class="progress-segment"></div>
		<div class="progress-segment"></div>
		<div class="progress-segment"></div>
	</div>
	<div class="team_outer_box">
		<div class="team_inner_box">
			<h1>Eispunkt</h1>
		</div>
		<div class="points_buttons_outer_box">
			<div on:click={() => deletePoint(1)} class="points_button">
				<p class="minus">-</p>
			</div>
			<h2 id="t1SubScoreText">0</h2>
			<div on:click={() => addPoint(1)} class="points_button">
				<p>+</p>
			</div>
		</div>
	</div>
	<div class="team_outer_box">
		<div class="team_inner_box">
			<h1>Eisbär</h1>
		</div>
		<div class="points_buttons_outer_box">
			<div on:click={() => deletePoint(2)} class="points_button">
				<p class="minus">-</p>
			</div>

			<h2 id="t2SubScoreText">0</h2>
			<div on:click={() => addPoint(2)} class="points_button">
				<p>+</p>
			</div>
		</div>
	</div>
	<div class="overlay" id="overlay">
		<div class="modal">
			<h2 id="winnerText">Team 1 wins</h2>
			<button class="save-btn" on:click={resetGame}> Restart Game </button>

			<button class="save-btn">
				<a href="/">Home</a>
			</button>
		</div>
	</div>
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
		font-size: 50px;
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
		margin-top: 10%;
		align-items: center;
	}

	.team_inner_box {
		width: 100%;
		height: 30%;
		text-align: center;
	}
	.team_inner_box > h1 {
		font-size: 300%;
		margin-bottom: 5%;
	}

	.points_buttons_outer_box {
		background-color: rgba(127, 200, 238, 0.33);
		border-radius: 20px;
		width: 85vw;
		display: flex;
		flex-direction: row;
		justify-content: center;
		align-items: center;
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
		height: 3vh;
	}

	.progress-segment {
		flex: 1;
		height: 100%;
		background-color: #ccc;
		border-radius: 4px;
		transition: background-color 0.3s ease;
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
		display: none;
		align-items: center;
		justify-content: center;
		backdrop-filter: blur(6px);
		background-color: rgba(255, 255, 255, 0.4);
		z-index: 10;
	}

	:global(.overlay.active) {
		display: flex !important;
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
