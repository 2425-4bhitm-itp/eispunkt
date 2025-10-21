inputName = document.getElementById("name")
teamDetailsBox = document.getElementById("team-details-outer-box")

console.log("(GET) /api/team/0")
console.log("select * from TEAM;")

function openModal() {
    document.getElementById("overlay").classList.add("active");
}

function closeModal() {
    document.getElementById("overlay").classList.remove("active");
}

document.addEventListener("click", function(e) {
    const overlay = document.getElementById("overlay");
    if (e.target === overlay) {
        closeModal();
    }
});


function saveTeam(){
    console.log("(POST) /api/team/create/{teamName}")
    console.log("insert into TEAM (name) values ('{name}');")

    document.getElementById("overlay").classList.remove("active");
    teamDetailsBox.innerHTML +=
        `<div class='team-details' id="team2">
            <h1>${inputName.value}</h1>
            <div class="svg-box">
                <a href="../player/player.html">
                    <svg xmlns="http://www.w3.org/2000/svg" width="50" height="50" viewBox="0 0 20 20">
                        <path fill="#7FC8EE" d="m12.3 3.7l4 4L4 20H0v-4L12.3 3.7zm1.4-1.4L16 0l4 4l-2.3 2.3l-4-4z"/>
                    </svg>
                </a>
                <svg onclick="deleteTeam('team2')" width="60" height="60" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                    <path d="M6 19C6 20.1 6.9 21 8 21H16C17.1 21 18 20.1 18 19V9C18 7.9 17.1 7 16 7H8C6.9 7 6 7.9 6 
                    9V19ZM18 4H15.5L14.79 3.29C14.61 3.11 14.35 3 14.09 3H9.91C9.65 3 9.39 3.11 9.21 3.29L8.5 4H6C5.45 4 5 
                    4.45 5 5C5 5.55 5.45 6 6 6H18C18.55 6 19 5.55 19 5C19 4.45 18.55 4 18 4Z" fill="#7FC8EE"/>
                </svg>
            </div>
        </div>`

}

function deleteTeam(teamBox) {
    console.log("DELETE /api/team/{teamId}")
    console.log("delete from TEAM where TEAMID = {teamId};")
    document.getElementById(teamBox).remove();
}

function teamDetails(){
    console.log("(GET) `/api/players/team/{teamId}`")
    console.log("select * from PLAYER where TEAM_TEAMID = {teamId} order by PLAYER_ID;")
    //window.location.href = "../player/player.html";
}