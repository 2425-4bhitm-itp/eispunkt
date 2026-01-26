package at.ac.htlleonding.websocket;

import at.ac.htlleonding.dto.TurnDto;
import at.ac.htlleonding.entities.Turn;
import at.ac.htlleonding.websocket.encoder.TurnDtoEncoder;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(
        value = "/ws/turns",
        encoders = {TurnDtoEncoder.class}
)
@ApplicationScoped
public class TurnWebSocket {
    private static final Set<Session> sessions = new CopyOnWriteArraySet<>();

    public void broadcastTurn(Turn turn) {
        System.out.println("BROADCASTING TURN: " + turn.getTurnId());
        for (Session s : sessions) {
            System.out.println("Sending to session: " + s.getId());
            s.getAsyncRemote().sendObject(TurnDto.fromEntity(turn));
        }
    }

    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);
        System.out.println("WS CONNECTED: " + session.getId());
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
    }

    @OnError
    public void onError(Session session, @PathParam("name") String name, Throwable throwable) {
        System.out.println("onError> " + name + ": " + throwable);
    }

    @OnMessage
    public void onMessage(String message, @PathParam("name") String name) {
        //Handle incoming messages if needed
    }
}
