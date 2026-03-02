import { browser } from '$app/environment';
import { keycloak, initKeycloak } from '$lib/keycloak';

export const load = async () => {
  if (!browser) return { authenticated: false, team: null };

  await initKeycloak();

  if (keycloak.authenticated) {
    const res = await fetch('/api/users/init', {
      method: 'POST',
      headers: { Authorization: `Bearer ${keycloak.token}` }
    });


    let team = await res.json();

    console.log(team);
    return { authenticated: true, team };
  }

  return { authenticated: false, team: null };
};