<script>
	import TeamListView from '$lib/components/TeamListView.svelte';
	import TeamDetailView from '$lib/components/TeamDetailView.svelte';

	let { data } = $props();
</script>

{#if data.isAdmin}
	<TeamListView />
{/if}

{#if data.isTeam && data.isAdmin == false}
	<TeamDetailView teamId={data.team.teamId} />
{/if}
