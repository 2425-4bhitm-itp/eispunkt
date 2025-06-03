<script lang="ts">
  import Header from "./Header.svelte";
</script>

<Header></Header>
<h1>gewählte Teams</h1>

<div id="firstTeam">
    <label id="team1">Team </label>
    <a class="editSvg" href="editTeam.html" >
        <svg width="100" height="50" viewBox="0 0 27 32" fill="none" xmlns="http://www.w3.org/2000/svg">
            <g id="Edit 2">
                <g id="Edit 2_2">
                    <path fill-rule="evenodd" clip-rule="evenodd" d="M16.0853 8.22186L6.93713 19.0642V23.7782H10.9146L20.0628 12.9359L16.0853 8.22186ZM9.18713 21.1115V20.1687L16.0853 11.9931L16.8808 12.9359L9.98263 21.1115H9.18713Z" fill="black"/>
                    <path d="M15.0003 23.7782H20.0628V21.1115H15.0003V23.7782Z" fill="black"/>
                </g>
            </g>
        </svg>
    </a>

    <h2 id="team1_player1"></h2>
    <h2 id="team1_player2"></h2>
    <h2 id="team1_player3"></h2>
    <h2 id="team1_player4"></h2>
</div>

<div id="secTeam">
    <label id="team2">Team </label>
    <a class="editSvg" href="editTeam.html" >
        <svg width="100" height="50" viewBox="0 0 27 32" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path fill-rule="evenodd" clip-rule="evenodd" d="M16.0853 8.22183L6.93713 19.0641V23.7782H10.9146L20.0628 12.9359L16.0853 8.22183ZM9.18713 21.1115V20.1687L16.0853 11.9931L16.8808 12.9359L9.98263 21.1115H9.18713Z" fill="black"/>
            <path d="M15.0003 23.7781H20.0628V21.1115H15.0003V23.7781Z" fill="black"/>
        </svg>
    </a>


    <h2 id="team2_player1"></h2>
    <h2 id="team2_player2"></h2>
    <h2 id="team2_player3"></h2>
    <h2 id="team2_player4"></h2>
</div>



<style>

    h1{
        text-align: center;
        font-size: 40px;

    }

    h2{
        font-size: 50px;
    }
    #exit{
        font-size: 100px;
        font-weight: bolder;
        text-decoration: none;
        margin-left: 3%;
        margin-top: 3%;
        color: black;
    }

    #team1 {
        font-size: 200%;
        font-weight: bold;

    }

    #team2{
        font-size: 200%;
        font-weight: bold;

    }

    .editSvg svg{
        margin-top: 2%;
    }

    h2{
        font-weight: lighter;
        margin-top: 4%;
    }

    #firstTeam{
        margin-left: 5%;
        margin-top: 3%;
    }

    #secTeam{
        margin-left: 5%;
        margin-top: 5%;
    }

    #playButton{
        width: 45%;
        padding: 4%;
        margin-top: 15%;
        margin-left: 23%;
        font-size: 300%;
        border-radius: 20px;
        border: none;
        background-color: #7FC8EE;
        color: white;
        display: flex;
        justify-content: center;
        align-items: center;
        text-decoration: none;
        text-align: center;
        font-weight: bold;
        box-shadow: 1px 15px 15px 7px rgba(0, 0, 0, 0.16);
    }

</style>