import { browser } from '$app/environment';
import { keycloak } from '$lib/keycloak';


export const load = async () => {
  if (browser && !keycloak.authenticated) {
    await keycloak.login();
  }

  const roles = keycloak.tokenParsed?.realm_access?.roles ?? [];

  return { 
    isTeam: roles.includes('team'),
    isAdmin: roles.includes('tournament-admin'),
  };
};