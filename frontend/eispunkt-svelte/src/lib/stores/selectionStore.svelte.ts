interface SelectionState {
    selectedTeam1: String;
    selectedTeam2: String;
}

export const selectionState = $state<SelectionState>({
    selectedTeam1: "",
    selectedTeam2: "",
});