if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', fillInputFields);
} else {
    fillInputFields();
}

function fillInputFields() {
    document.addEventListener('DOMContentLoaded', () => {

        const data = JSON.parse(sessionStorage.getItem('teamToEdit'));
        if (data) {
            console.log('team to edit:', data);
            const teamnameInput = document.getElementById('teamname') as HTMLSelectElement;
            teamnameInput.value = data.name;
            for (let i = 0; i < data.players.length; i++) {
                const playerInput = document.getElementById(`player${i + 1}`) as HTMLSelectElement;
                playerInput.value = data.players[i].name;
            }
        } else {
            console.error('No team data found in sessionStorage');
        }
    });

}

document.addEventListener('DOMContentLoaded', fillInputFields);