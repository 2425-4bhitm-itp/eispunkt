interface SelectionState {
  selectedTeam1: number;
  selectedTeam2: number;
  selectedTeam1Name: string;
  selectedTeam2Name: string;
  teamToEdit: number;
}

export const selectionState = $state<SelectionState>({
  selectedTeam1: 0,
  selectedTeam2: 0,
  selectedTeam1Name: "",
  selectedTeam2Name: "",
  teamToEdit: 0,
});
