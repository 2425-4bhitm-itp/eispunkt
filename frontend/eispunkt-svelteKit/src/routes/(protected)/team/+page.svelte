<script>
	import TeamListView from '$lib/components/TeamListView.svelte';
	import TeamDetailView from '$lib/components/TeamDetailView.svelte';
	import { user } from '$lib/stores/userStore';

</script>

{#if $user.role === 'TournamentAdmin'}
	<TeamListView />
{/if}

{#if $user.role === 'Team'}
	<TeamDetailView teamId={$user.team?.teamId} />
{/if}
