<script lang="ts">
    import TeamDetailView from "$lib/components/TeamDetailView.svelte";

    let { teamId } = $props();
</script>

<TeamDetailView teamId={teamId} />