interface NavigationState {
  currentPane: String;
}

export const navigationState = $state<NavigationState>({
  currentPane: "ChooseTeam",
});
