package at.ac.htlleonding.dto;

import at.ac.htlleonding.entities.Turn;

public record TurnDto(long turnId, long stageId, long teamId, int turnNumber, int score, long gameId) {
    public static TurnDto fromEntity(Turn turn) {
        return new TurnDto(turn.getTurnId(), turn.getStage().getStageId(),
                           turn.getTeam().getTeamId(), turn.getTurnNumber(), turn.getScore(), turn.getStage().getGame().getGameId());
    }
}
