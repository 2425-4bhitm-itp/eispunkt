<script lang="ts">
    import TeamDetailView from "$lib/components/TeamDetailView.svelte";

    let { data } = $props();
</script>

<TeamDetailView teamId={data.team.teamId} />