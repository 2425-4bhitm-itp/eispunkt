import { persisted } from "svelte-persisted-store";

export const selectedTeams = persisted('teams', {
    selectedTeams: [0,0]
})

export const selectedTournament = persisted('tournament',
    {
        selectedTournament: 0
    }
)