
function addTeam(teamNum){
    let teamName = document.getElementById(`team${teamNum}`);

    fetch("http://localhost:8080/team/addTeam", {
        method: 'post',
        body: teamName,
    }).then((response) =>{
        if(!response.ok){
            console.log(response.status)
        }else{
            console.log("Team added Successfully")
        }
    })
}