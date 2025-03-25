<script lang="ts">
    async function listTeams() {
        try {
            const response = await fetch('http://localhost:8080/api/team/getAllTeams')

            if (!response.ok) {
                throw new Error('http fehler status: ${response.status}')
            }

            const teams = await response.text()
            console.log(teams)
            let teamsJSON = JSON.parse(teams);

            const dropdown_team1 = document.getElementById('firstTeam') as HTMLSelectElement;
            const dropdown_team2 = document.getElementById('secTeam') as HTMLSelectElement;

            dropdown_team1.innerHTML = '<option disabled value="">Bitte wählen...</option>'
            dropdown_team2.innerHTML = '<option disabled value="">Bitte wählen...</option>'

            teamsJSON.forEach((team: { teamId: number, name: string }) => {
                const option1 = document.createElement('option');
                option1.value = team.teamId.toString();
                option1.textContent = team.name;
                dropdown_team1.appendChild(option1);

                const option2 = document.createElement('option');
                option2.value = team.teamId.toString();
                option2.textContent = team.name;
                dropdown_team2.appendChild(option2);

                console.log(team.name);

                dropdown_team1.addEventListener('change', () => {
                    const selectedTeam1 = dropdown_team1.value;
                    sessionStorage.setItem('selectedTeam1', selectedTeam1);
                    console.log(`Ausgewähltes Team 1: ${selectedTeam1}`)
                })

                dropdown_team2.addEventListener('change', () => {
                    const selectedTeam2 = dropdown_team2.value;
                    sessionStorage.setItem('selectedTeam2', selectedTeam2);
                    console.log(`Ausgewähltes Team 2: ${selectedTeam2}`)
                })
            });

        } catch (error) {
            console.error('Error: ', error);
        }
    }


</script>



<header id="header">Eispunkt</header>
<h1>Teams auswählen</h1>

<div id="selectBox">
    <select id="firstTeam" class="dropdown"></select>

    <select id="secTeam" class="dropdown"></select>
</div>

<div id="createTeam">
    <h2>neues Team erstellen</h2>
    <a id="addButton" href="createTeam.html">+</a>
</div>

<a id="nextButton" href="teamOverview.html">Weiter</a>


<style>
    *{
        margin: 0;
    }

    #header{
        background-color: #45CAAC;
        padding: 3%;
        text-align: center;
        color: white;
        font-size: 350%;
        font-weight: bold;
    }

    h1{
        text-align: center;
        font-size:40px;
        margin-top: 5%;
    }

    #selectBox{
        display: block;
    }


    #firstTeam{
        width: 90%;
        margin-left: 5%;
        font-size: 50px;
        border: 1px solid #ccc;
        border-radius: 8px;
        background-color: white;
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        margin-top: 15%;

    }


    #firstTeam:focus {
        border-color: rgba(69, 202, 172, 0.33) ;
        outline: none;
        box-shadow: 0 0 5px rgba(69, 202, 172, 0.33);
    }


    #secTeam{
        width: 90%;
        margin-left: 5%;
        font-size: 50px;
        border: 1px solid #ccc;
        border-radius: 8px;
        background-color: white;
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        margin-top: 20%;

    }


    #secTeam:focus {
        border-color: rgba(69, 202, 172, 0.33) ;
        outline: none;
        box-shadow: 0 0 5px rgba(69, 202, 172, 0.33);
    }

    #createTeam{
        display: flex;
        width: 100%;
        margin-top: 15%;
        justify-content: center;
    }

    #addButton{
        width: 13%;
        padding: 1%;
        margin-left: 15%;
        font-size: 150%;
        border-radius: 25px;
        border: none;
        background-color: #7FC8EE;
        color: white;
        display: flex;
        justify-content: center;
        align-items: center;
        text-decoration: none;
        text-align: center;
        font-weight: bold;
    }

    #createTeam h2{
        font-size: 150%;
        margin-top: 2%;
    }

    #nextButton{
        width: 45%;
        padding: 3%;
        margin-top: 50%;
        margin-left: 23%;
        font-size: 200%;
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