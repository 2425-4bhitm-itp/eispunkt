<script lang="ts">
	import TeamDetailView from '$lib/components/TeamDetailView.svelte';

	const { data } = $props();
    console.log(data);
	const teamId = $derived(data.teamId);

	$inspect(teamId);
</script>

<TeamDetailView {teamId}/>