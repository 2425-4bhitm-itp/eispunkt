<script lang="ts">
    type Player = {
        id: number;
        name: string;
    };

    type Team = {
        id: number;
        name: string;
        players?: Player[];
    };

    type Game = {
        id: number;
        team1: Team;
        score1: number;
        team2: Team;
        score2: number;
    };

    const teams: Team[] = [
        {
            id: 1,
            name: 'Team 3',
            players: [
                { id: 1, name: 'Max' },
                { id: 2, name: 'Lukas' },
                { id: 3, name: 'Tobias' },
                { id: 4, name: 'Jonas' }
            ]
        },
        {
            id: 2,
            name: 'Team 4',
            players: [
                { id: 5, name: 'Paul' },
                { id: 6, name: 'David' },
                { id: 7, name: 'Simon' },
                { id: 8, name: 'Noah' }
            ]
        },
        { id: 3, name: 'Team 7' },
        { id: 4, name: 'Team 8' },
        { id: 5, name: 'Team 9' },
        { id: 6, name: 'Team 10' }
    ];

    let activeGames: Game[] = [
        { id: 1, team1: teams[0], score1: 5, team2: teams[1], score2: 1 },
        { id: 2, team1: teams[2], score1: 5, team2: teams[3], score2: 1 },
        { id: 3, team1: teams[4], score1: 2, team2: teams[5], score2: 2 },
        { id: 4, team1: teams[0], score1: 5, team2: teams[1], score2: 1 },
        { id: 5, team1: teams[2], score1: 5, team2: teams[3], score2: 1 },
        { id: 6, team1: teams[3], score1: 4, team2: teams[1], score2: 3 }
    ];


    let selectedGame = $state<Game | null>(activeGames[0]);

    function selectGame(game: Game) {
        selectedGame = game;
    }

    const segments = Array(13).fill(0);
</script>

<div class="page">
    <header class="header">Eispunkt</header>

    <div class="layout">

        <section class="left">
            <div class="card">
                <h1>Aktive Spiele</h1>

                {#each activeGames as g (g.id)}
                    <div
                            class="match"
                            class:selected={selectedGame?.id === g.id}
                            on:click={() => selectGame(g)}
                    >
                        <div class="match-row">
                            <span>{g.team1.name}</span>
                            <strong>{g.score1}</strong>
                        </div>
                        <div class="match-row">
                            <span>{g.team2.name}</span>
                            <strong>{g.score2}</strong>
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
                    <p>{selectedGame.score1}</p>
                    <h2>:</h2>
                    <p>{selectedGame.score2}</p>
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
        font-size: clamp(2rem, 4vw, 3rem);
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

    .match {
        background: white;
        border-radius: var(--radius);
        box-shadow: var(--shadow);
        padding: 1rem;
        margin-top: 1rem;
        cursor: pointer;
        transition: transform 0.15s ease, box-shadow 0.15s ease;
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

    @media (max-width: 900px) {
        .layout {
            grid-template-columns: 1fr;
        }
    }
</style>
