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

    let team = null;
    if (res.ok && res.status !== 304) {
      team = await res.json();
    }

    return { authenticated: true, team };
  }

  return { authenticated: false, team: null };
};