<script>
	import TeamListView from "../../../components/TeamListView.svelte";
	import TeamDetailView from "../../../components/TeamDetailView.svelte";
	
let {data} = $props();
</script>

{#if data.isAdmin}
	<TeamListView/>
{/if}

{#if data.isTeam}
	<TeamDetailView teamId={data.team.teamId}/>
{/if}
