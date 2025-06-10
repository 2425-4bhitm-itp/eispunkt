interface gameState {
  gameId: number;
  currentStageId: number;
  currentTurnId: number;
  stageNumber: number;
  turnNumber: number;
  team1SubScore: number;
  team1Score: number;
  team2Score: number;
  team2SubScore: number;
  totalPointsThisTurn: number;
  isProcessingTurn: boolean;
  winner: number | null;
  gameFinished: boolean;
  turnHistory: Array<'team1' | 'team2' | 'draw'>;
}

export const gameState = $state<gameState>({
  gameId: 0,
  currentStageId: 0,
  currentTurnId: 0,
  stageNumber: 1,
  turnNumber: 1,
  team1SubScore: 0,
  team1Score: 0,
  team2Score: 0,
  team2SubScore: 0,
  totalPointsThisTurn: 0,
  isProcessingTurn: false,
  winner: null,
  gameFinished: false,
  turnHistory: [],
});
