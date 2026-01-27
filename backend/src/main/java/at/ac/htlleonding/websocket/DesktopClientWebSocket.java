package at.ac.htlleonding.websocket;

import at.ac.htlleonding.dto.ActiveGameDTO;
import at.ac.htlleonding.dto.TurnDto;
import at.ac.htlleonding.dto.WsMessage;
import at.ac.htlleonding.entities.Game;
import at.ac.htlleonding.entities.Turn;
import at.ac.htlleonding.repositories.GameRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import org.eclipse.microprofile.context.ManagedExecutor;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint("/ws/games")
@ApplicationScoped
public class DesktopClientWebSocket {

    private static final Set<Session> sessions = new CopyOnWriteArraySet<>();

    @Inject
    GameRepository gameRepository;

    @Inject
    ManagedExecutor executor;

    private final Jsonb jsonb = JsonbBuilder.create();


    public void broadcastActiveGames() {
        executor.runAsync(() -> {
            try {
                List<ActiveGameDTO> activeGames =
                        gameRepository.list("isActive = true")
                                .stream()
                                .map(ActiveGameDTO::createFromGame)
                                .toList();

                String json = jsonb.toJson(
                        new WsMessage<>("active-games", activeGames)
                );

                sessions.forEach(s ->
                        s.getAsyncRemote().sendText(json)
                );

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    public void broadcastGameUpdate(Game game) {
        String json = jsonb.toJson(
                new WsMessage<>("game-update",
                        ActiveGameDTO.createFromGame(game))
        );

        sessions.forEach(s ->
                s.getAsyncRemote().sendText(json)
        );
    }


    public void broadcastTurn(Turn turn) {
        String json = jsonb.toJson(
                new WsMessage<>("turn-update",
                        TurnDto.fromEntity(turn))
        );

        sessions.forEach(s ->
                s.getAsyncRemote().sendText(json)
        );
    }


    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);
        System.out.println("Desktop connected: " + session.getId());
        broadcastActiveGames();
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
        System.out.println("Desktop disconnected: " + session.getId());
    }

    @OnError
    public void onError(Session session, Throwable t) {
        t.printStackTrace();
    }
}