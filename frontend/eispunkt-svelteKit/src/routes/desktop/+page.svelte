<script lang="ts">
	import { onMount } from 'svelte';

	let socket: WebSocket;

	type Player = {
		name: string;
	};

	type Team = {
		teamId: number;
		name: string;
		players?: Player[];
	};

	type Game = {
		gameId: number;
		team1: Team;
		team1Score: number;
		team2: Team;
		team2Score: number;
		currentStage: number;
	};

	type Segment = {
		teamIndex: number;
		score: number;
	};

	let activeGames: Game[] = $state([]);
	let isPatrolling = $state(false);
	let selectedPatrolIndex = $state(0);
	let selectedGame = $state<Game | null>(null);
	let connectionStatus = $state<'connecting' | 'connected' | 'disconnected'>('disconnected');
	let patrolIntervalId: number | null = null;

	let segments: Segment[] = $state([]);

	function selectGame(game: Game) {
		selectedGame = game;
        selectedPatrolIndex = activeGames.findIndex(g => g.gameId === game.gameId);
	}

	function togglePatrol() {
		isPatrolling = !isPatrolling;
		if (isPatrolling) {
			patrolIntervalId = setInterval(() => {
				selectedPatrolIndex = (selectedPatrolIndex + 1) % activeGames.length;
				selectedGame = activeGames[selectedPatrolIndex];
			}, 5000) as unknown as number;
		} else {
			if (patrolIntervalId !== null) {
				clearInterval(patrolIntervalId);
				patrolIntervalId = null;
			}
			selectedPatrolIndex = 0;
		}
	}

	onMount(() => {
		socket = new WebSocket('ws://localhost:8080/ws/games');

		socket.onopen = (event) => {
			console.log('Desktop WebSocket connected:');
			connectionStatus = 'connected';
		};

		socket.onmessage = (event) => {
			try {
				const msg = JSON.parse(event.data);

				switch (msg.type) {
					case 'active-games':
						activeGames = msg.payload;
						if (!selectedGame && activeGames.length > 0) {
							selectedGame = activeGames[selectedPatrolIndex];
						}
						break;

					case 'game-update':
						console.log(msg.payload);
						const index = activeGames.findIndex((g) => g.gameId === msg.payload.gameId);
						if (index >= 0) {
							activeGames[index] = msg.payload;
							activeGames = [...activeGames];
						}
						if (selectedGame?.gameId === msg.payload.gameId) {
							selectedGame = msg.payload;
						}
						break;

					case 'turn-update':
						/*if(selectedGame?.currentStage == msg.payload.stageId){
                            let teamIndex = msg.payload.teamId === selectedGame?.team1.teamId ? 0 : 1;
                            segments[msg.payload.turnNumber-1] = {
                                teamIndex,
                                score: msg.payload.score
                            };
                        }else{
                            segments = []
                        }*/
						break;
				}
			} catch (e) {
				console.error('WS parse error', e);
			}
		};

		socket.onerror = (error) => {
			console.error('WebSocket error:', error);
			connectionStatus = 'disconnected';
		};

		socket.onclose = () => {
			console.log('Desktop WebSocket disconnected');
			connectionStatus = 'disconnected';
		};

		return () => {
			if (patrolIntervalId !== null) {
				clearInterval(patrolIntervalId);
			}
			if (socket && socket.readyState === WebSocket.OPEN) {
				socket.close();
			}
		};
	});
</script>

<div class="page">
	<header class="header">Eispunkt</header>

	<div class="layout">
		<section class="left">
			<div class="card">
				<div class="row">
					<h1>Aktive Spiele</h1>
					<button onclick={togglePatrol} class={isPatrolling ? 'active' : ''}>Patrol</button>
				</div>
				{#if connectionStatus !== 'connected'}
					<div class="status-message">
						Status: {connectionStatus}
					</div>
				{/if}

				{#each activeGames as game (game.gameId)}
					<div
						class="match"
						class:selected={selectedGame?.gameId === game.gameId}
						onclick={() => selectGame(game)}
					>
						<div class="match-row">
							<span>{game.team1.name}</span>
							<strong>{game.team1Score}</strong>
						</div>
						<div class="match-row">
							<span>{game.team2.name}</span>
							<strong>{game.team2Score}</strong>
						</div>
					</div>
				{/each}
			</div>
		</section>

		<section class="right">
			<div class="progress">
				{#each segments as _}
					<div class="segment"></div>
				{/each}
			</div>

			{#if selectedGame}
				<div class="score-box">
					<p>{selectedGame.team1Score}</p>
					<h2>:</h2>
					<p>{selectedGame.team2Score}</p>
				</div>

				<div class="team-box">
					<div class="team">
						<h3>{selectedGame.team1.name}</h3>

						{#if selectedGame.team1.players}
							<ul class="player-list">
								{#each selectedGame.team1.players as p}
									<li>{p.name}</li>
								{/each}
							</ul>
						{/if}
					</div>

					<div class="team">
						<h3>{selectedGame.team2.name}</h3>

						{#if selectedGame.team2.players}
							<ul class="player-list">
								{#each selectedGame.team2.players as p}
									<li>{p.name}</li>
								{/each}
							</ul>
						{/if}
					</div>
				</div>
			{/if}
		</section>
	</div>
</div>

<style>
	:root {
		--primary: #45caac;
		--accent: #7fc8ee;
		--bg: #f8f8f8;
		--shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
		--radius: 14px;
		font-family: 'Afacad', sans-serif;
	}

	.page {
		height: 100vh;
		overflow: hidden;
		display: flex;
		flex-direction: column;
	}

	.header {
		background: var(--primary);
		color: white;
		text-align: center;
		font-family: 'Protest Strike', sans-serif;
		font-size: 50px;
        font-weight:bold;
		padding: 1rem;
	}

	.layout {
		flex: 1;
		display: grid;
		grid-template-columns: 2fr 3fr;
		gap: 1.5rem;
		padding: 1.5rem;
		overflow: hidden;
	}

	.right {
		background: var(--bg);
		border-radius: var(--radius);
		box-shadow: var(--shadow);
		padding: 1.5rem;
		display: flex;
		flex-direction: column;
		gap: 2rem;
	}

	.progress {
		display: flex;
		gap: 4px;
		height: 14px;
	}

	.segment {
		flex: 1;
		height: 3vh;
		background: #ccc;
		border-radius: 4px;
	}

	.score-box {
		display: flex;
		justify-content: center;
		align-items: center;
		gap: 2rem;
		background: var(--accent);
		border-radius: var(--radius);
		color: white;
		font-family: 'Protest Strike', sans-serif;
		font-size: 2.5rem;
		padding: 1rem;
	}

	.team-box {
		display: grid;
		grid-template-columns: 1fr 1fr;
		gap: 1.5rem;
		border-radius: var(--radius);
		padding: 1.5rem;
	}

	.team {
		background: rgba(127, 200, 238, 0.25);
		border-radius: var(--radius);
		padding: 1rem;
		display: flex;
		flex-direction: column;
		gap: 0.5rem;
		box-shadow: var(--shadow);
	}

	.team h3 {
		text-align: center;
		font-size: 1.4rem;
		color: var(--primary);
		margin-bottom: 0.5rem;
	}

	.player-list {
		list-style: none;
		padding: 0;
		margin: 0;
		display: flex;
		flex-direction: column;
		gap: 0.3rem;
	}

	.player-list li {
		border-radius: 8px;
		padding: 0.4rem 0.6rem;
		text-align: center;
		font-size: 1.2rem;
		box-shadow: var(--shadow);
	}

	.left {
		display: flex;
		flex-direction: column;
		overflow: hidden;
	}

	.card {
		background: var(--bg);
		border-radius: var(--radius);
		box-shadow: var(--shadow);
		padding: 1.5rem;
		display: flex;
		flex-direction: column;
		min-height: 0;
		overflow-y: auto;
	}

	.row {
		display: flex;
		flex-direction: row;
		justify-content: space-between;
	}

	button {
		width: 40%;
		height: 60%;
		margin-top: 5%;
		background: var(--primary);
		color: white;
		border: none;
		border-radius: var(--radius);
		padding: 0.5rem 1rem;
		font-size: 1rem;
		cursor: pointer;
		box-shadow: var(--shadow);
	}

    .active{
        background-color: var(--bg);
        border:3px solid var(--accent);
        color:black;
    }

	.match {
		background: white;
		border-radius: var(--radius);
		box-shadow: var(--shadow);
		padding: 1rem;
		margin-top: 1rem;
		cursor: pointer;
		transition:
			transform 0.15s ease,
			box-shadow 0.15s ease;
	}

	.match:hover {
		transform: scale(1.02);
	}

	.selected {
		border: 2px solid var(--accent);
	}

	.match-row {
		display: flex;
		justify-content: space-between;
		font-size: 1.2rem;
		margin: 0.3rem 0;
	}

	.match strong {
		color: var(--accent);
		font-size: 1.4rem;
	}

	.status-message {
		background: #fff3cd;
		border: 1px solid #ffc107;
		color: #856404;
		padding: 0.75rem;
		border-radius: var(--radius);
		margin-bottom: 1rem;
		text-align: center;
		font-size: 0.9rem;
	}

	@media (max-width: 900px) {
		.layout {
			grid-template-columns: 1fr;
		}
	}
</style>
