import { persisted } from "svelte-persisted-store";

export const selection = persisted('selection', {
    selectedTeams: [],
    tournamentId: null,
    
})