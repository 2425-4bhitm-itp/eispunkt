<script lang="ts">
	import TeamDetailView from '$lib/components/TeamDetailView.svelte';

	const { data } = $props();
	const teamId = $derived(data.teamId);

	$inspect(teamId);
</script>

<TeamDetailView {teamId} />