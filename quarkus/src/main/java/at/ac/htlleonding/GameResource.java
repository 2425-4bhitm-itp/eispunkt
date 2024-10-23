package at.ac.htlleonding;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

//import javax.json.bind.Jsonb;
//import javax.json.bind.JsonbBuilder;
import java.time.LocalDate;

@Path("/game")
public class GameResource {
    public static Game currentGame = new Game(LocalDate.now());

    @Path("/startGame")
    @POST
    public Response startGame(){
        currentGame.getTeams().stream().forEach(team -> team.resetScore());
        return Response.ok("reset Score for all Teams").build();
    }

    @Path("/resetGame")
    @POST
    public Response resetGame(){
        currentGame = new Game(LocalDate.now());
        return Response.ok("created new Game").build();
    }

    @Path("/modifyScore")
    @POST
    public Response incrementScore(
            @QueryParam("team") String teamName,
            @QueryParam("type") String type){
        if(type == "+"){
            currentGame.getTeams().stream().
                    filter(team-> team.getName().equalsIgnoreCase(teamName)).
                    findFirst()
                    .ifPresent(team -> team.addScore(1));
        }else if(type == "-"){
            currentGame.getTeams().stream().
                    filter(team-> team.getName().equalsIgnoreCase(teamName)).
                    findFirst().
                    ifPresent(team -> team.addScore(-1));
        }else{
            return Response.noContent().build();
        }

        return Response.ok().build();
    }


    /*@Path("/getGameInfo")
    @GET
    public Response getInfo(){
        Jsonb jsonb = JsonbBuilder.create();
        return Response.ok(jsonb.toJson(currentGame)).build();
    }*/
}
