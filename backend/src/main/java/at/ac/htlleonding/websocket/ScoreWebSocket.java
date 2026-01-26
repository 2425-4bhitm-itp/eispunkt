package at.ac.htlleonding.websocket;

import at.ac.htlleonding.dto.Score;
import at.ac.htlleonding.dto.TurnDto;
import at.ac.htlleonding.entities.Game;
import at.ac.htlleonding.entities.Turn;
import at.ac.htlleonding.repositories.GameRepository;
import at.ac.htlleonding.websocket.encoder.ScoreEncoder;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.eclipse.microprofile.context.ManagedExecutor;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;


@ServerEndpoint(
        value = "/ws/scores/{gameId}",
        encoders = {ScoreEncoder.class}
)
@ApplicationScoped
public class ScoreWebSocket {
    @Inject
    GameRepository gameRepository;

    @Inject
    ManagedExecutor executor;

    private static final Set<Session> sessions = new CopyOnWriteArraySet<>();

    public void broadcastScore(Game game) {
        System.out.println("BROADCASTING Game: " + game.getGameId());
        for (Session s : sessions) {
            System.out.println("Sending to session: " + s.getId());
            s.getAsyncRemote().sendObject(Score.createFromGame(game));
        }
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("gameId") Long gameId) {
        System.out.println("GameId: " + gameId);
        sessions.add(session);
        System.out.println("WS CONNECTED: " + session.getId());

        executor.runAsync(() -> {
            try {
                gameRepository.changeActive(gameId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
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
