function fillInputFields() {

    document.addEventListener('DOMContentLoaded', () => {

        const data = JSON.parse(sessionStorage.getItem('teamToEdit'));
        console.log('team to edit:', data);
        if (data) {
            console.log('team to edit:', data);
            document.getElementById('teamname').textContent = data.name;
            for (let i = 0; i < data.players.length; i++) {
                document.getElementById(`player${i + 1}`).textContent = data.players[i].name;
            }
        } else {
            console.error('No team data found in sessionStorage');
        }
    });

}

document.addEventListener('DOMContentLoaded', fillInputFields);