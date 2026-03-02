<script lang="ts">
	import { onMount } from 'svelte';
	import { browser } from '$app/environment';
	import { keycloak, initKeycloak } from '$lib/keycloak';
	import { user } from '$lib/stores/userStore';

	let ready = false;

	onMount(async () => {
		if (!browser) return;

		await initKeycloak();

		if (!keycloak.authenticated) {
			await keycloak.login({
				redirectUri: window.location.href
			});
			return;
		}

		const res = await fetch('https://it200230.cloud.htl-leonding.ac.at/api/users/init', {
			method: 'POST',
			headers: {
				Authorization: `Bearer ${keycloak.token}`
			}
		});

		const team = await res.json();

		user.set({
			authenticated: true,
			team,
            role: keycloak.tokenParsed?.realm_access?.roles.includes('TournamentAdmin') ? 'TournamentAdmin' : 'Team'
		});

		ready = true;
	});
</script>

{#if ready}
	<slot />
{/if}