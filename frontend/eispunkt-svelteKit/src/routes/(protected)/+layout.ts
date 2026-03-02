import { browser } from '$app/environment';
import { keycloak } from '$lib/keycloak';


export const load = async () => {
  if (!browser) return {};

  if (!keycloak.authenticated) {
    await keycloak.login({
      redirectUri: window.location.origin + '/'
    });
  }

  const roles = keycloak.tokenParsed?.realm_access?.roles ?? [];
  return { 
    isTeam: roles.includes('team'),
    isAdmin: roles.includes('tournament-admin'),
  };
};