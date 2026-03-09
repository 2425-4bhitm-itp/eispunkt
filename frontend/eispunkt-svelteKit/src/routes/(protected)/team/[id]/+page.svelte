<script lang="ts">
	import { goto } from '$app/navigation';
	import TeamDetailView from '$lib/components/TeamDetailView.svelte';
	import { user } from '$lib/stores/userStore.js';

	const { data } = $props();
	const teamId = $derived(data.teamId);

	if($user.role != 'TournamentAdmin'){
		goto('/team')
	}

	
</script>

<TeamDetailView {teamId}/>