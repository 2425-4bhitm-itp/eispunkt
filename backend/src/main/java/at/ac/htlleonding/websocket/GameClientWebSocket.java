package at.ac.htlleonding.websocket;

import at.ac.htlleonding.service.GameService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.eclipse.microprofile.context.ManagedExecutor;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;


@ServerEndpoint(value = "/ws/scores/{gameId}", encoders = {})
@ApplicationScoped
public class GameClientWebSocket {
    private static final Set<Session> sessions = new CopyOnWriteArraySet<>();
    @Inject
    GameService gameService;
    @Inject
    ManagedExecutor executor;
    @Inject
    DesktopClientWebSocket desktopClientWebSocket;


    @OnOpen
    public void onOpen(Session session, @PathParam("gameId") Long gameId) {
        System.out.println("GameId: " + gameId);
        sessions.add(session);
        System.out.println("WS CONNECTED: " + session.getId());

        executor.runAsync(() -> {
            try {
                System.out.println("Changing game " + gameId + " to active");
                gameService.changeActive(gameId, true);
                desktopClientWebSocket.broadcastActiveGames();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @OnClose
    public void onClose(Session session, @PathParam("gameId") Long gameId) {
        System.out.println("WS DISCONNECTED: " + session.getId());
        executor.runAsync(() -> {
            try {
                System.out.println("Changing game " + gameId + " to inactive");
                gameService.changeActive(gameId, false);
                desktopClientWebSocket.broadcastActiveGames();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
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
