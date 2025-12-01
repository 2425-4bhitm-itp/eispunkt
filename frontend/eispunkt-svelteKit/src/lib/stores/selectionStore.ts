import { persisted } from "svelte-persisted-store";

export const selectedTeams = persisted('teams', {
    selectedTeams: []
})

export const selectedTournament = persisted('tournament',
    {
        selectedTournament: 0
    }
)