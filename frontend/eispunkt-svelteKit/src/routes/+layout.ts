import { browser } from '$app/environment';
import { keycloak } from '$lib/keycloak';

let initialized = false;

export const load = async () => {
  if (!browser) return {};

  if (!initialized) {
    await keycloak.init({
      onLoad: 'check-sso',
      silentCheckSsoRedirectUri: `${window.location.origin}/silent-check-sso.html`,
      checkLoginIframe: false
    });
    initialized = true;
  }

  if (keycloak.authenticated) {
    const res = await fetch('/api/users/init', {
      method: 'POST',
      headers: { Authorization: `Bearer ${keycloak.token}` }
    });
    const team = await res.json();
    return { authenticated: true, team };
  }

  return { authenticated: false, team: null };
};