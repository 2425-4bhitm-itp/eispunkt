function addTeam(teamNum){
    let teamName = document.getElementById(`team${teamNum}`);

    fetch("http://localhost:8080/team/addTeam", {
        method: 'POST',
        body: teamName,
    }).then((response) =>{
        if(!response.ok){
            console.log(response)
        }else{
            console.log("Team added Successfully")
        }
    })
}