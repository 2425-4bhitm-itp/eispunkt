<script lang="ts">
    import TeamDetailView from "$lib/components/TeamDetailView.svelte";

    let { teamId } = $props();
    console.log(teamId)
</script>

<TeamDetailView teamId={teamId} />