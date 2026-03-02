<script lang="ts">
    import TeamDetailView from "../../../components/TeamDetailView.svelte";

    let { data } = $props();
</script>

<TeamDetailView teamId={data.team.teamId} />