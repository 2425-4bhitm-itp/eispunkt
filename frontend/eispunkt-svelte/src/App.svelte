<script lang="ts">
    import ChooseTeam from "./lib/components/ChooseTeam.svelte";
    import CreateTeam from "./lib/components/CreateTeam.svelte";
    import EditTeam from "./lib/components/EditTeam.svelte";
    import FirstPage from "./lib/components/FirstPage.svelte";
    import Game from "./lib/components/Game.svelte";
    import TeamOverview from "./lib/components/TeamOverview.svelte";

    import { navigationState } from "./lib/stores/navigationStore.svelte";

    const componentMap = {
        FirstPage: FirstPage,
        ChooseTeam: ChooseTeam,
        CreateTeam: CreateTeam,
        EditTeam: EditTeam,
        Game: Game,
        TeamOverview: TeamOverview,
    };
</script>

<svelte:component this={componentMap[navigationState.currentPane]}/>
