package at.ac.htlleonding.service;

import at.ac.htlleonding.entities.Game;
import at.ac.htlleonding.repositories.GameRepository;
import at.ac.htlleonding.websocket.DesktopClientWebSocket;
import at.ac.htlleonding.websocket.GameClientWebSocket;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class GameService {

    @Inject
    GameRepository gameRepository;

    @Inject
    DesktopClientWebSocket desktopClientWebSocket;

    @Transactional
    public void changeActive(Long gameId, boolean active) {
        gameRepository.update(
                "isActive = ?1" +
                "where gameId = ?2",
                active,
                gameId);
        gameRepository.getEntityManager().flush();
        desktopClientWebSocket.broadcastActiveGames();
    }

    @Transactional
    public List<Game> getActiveGameIds() {
        return gameRepository.list("isActive = true");
    }
}