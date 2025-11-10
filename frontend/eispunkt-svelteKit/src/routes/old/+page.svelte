<script>
</script>
<div id="body-div">
    <div class="circle">
        <h1>Willkommen bei<br /><span>Eispunkt</span></h1>
    </div>
    <div class="dropdown-container">
        <label class="dropdown-label">Selektieren Sie einen Spielmodus</label>
        <select id="game-mode" class="dropdown">
            <option value="" disabled selected>Select game</option>
            <option value="spiel1">spiel</option>
            <option value="spiel2">spiel</option>
            <option value="spiel3">spiel</option>
        </select>
    </div>
    <div id="button-div">
        <a id="startButton" href="/teamSelect">Continue</a>
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
    }

    #button-div {
        display: flex;
        justify-content: center;
        align-items: center;
        width: 100%;
        height: 10%;
        margin-top: 30%;
    }

    #startButton {
        width: 70%;
        height: 50%;
        padding: 4%;
        font-size: 200%;
        border-radius: 20px;
        border: none;
        background-color: #7fc8ee;
        color: white;
        display: flex;
        justify-content: center;
        align-items: center;
        text-decoration: none;
        text-align: center;
        font-weight: bold;
        box-shadow: 1px 15px 15px 7px rgba(0, 0, 0, 0.16);
    }

	.circle {
		position: relative;
		left: 30%;
		transform: translateX(-50%);
		width: 140vw;
		height: 80vw;
		background-color: #45caac;
		border-radius: 0 0 80% 50%;
		display: flex;
		justify-content: center;
		align-items: center;
	}

	.circle h1 {
		color: white;
		font-size: 30px;
		text-align: center;
		line-height: 1.4;
		margin-left: 5%;
		margin-top: -5%;
		font-weight: lighter;
		font-family: 'Protest Strike', serif;
		font-weight: 400;
		font-style: normal;
	}

	.circle h1 span {
		font-weight: bold;
		font-size: 70px;
		font-family: 'Protest Strike', serif;
		font-weight: 400;
		font-style: normal;
		margin-left: 12%;
	}

	.dropdown-container {
		width: 100%;
		margin-bottom: 20px;
	}

	.dropdown-label {
		font-size: 150%;
		font-weight: bold;
		margin-bottom: 8px;
		display: block;
		margin-top: 10%;
		margin-left: 5%;
		margin-bottom: 5%;
		font-family: 'Afacad', serif;
		font-optical-sizing: auto;
		font-style: normal;
	}

	.dropdown {
		width: 90%;
		padding: 3%;
		margin-left: 4%;
		font-size: 100%;
		border: 1px solid #ccc;
		border-radius: 8px;
		background-color: white;
		box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
	}

	.dropdown:focus {
		border-color: rgba(69, 202, 172, 0.33);
		outline: none;
		box-shadow: 0 0 5px rgba(69, 202, 172, 0.33);
	}
</style>
