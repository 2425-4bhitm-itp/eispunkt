import { browser } from '$app/environment';
import { keycloak } from '$lib/keycloak';


export const load = async () => {
  if (browser && !keycloak.authenticated) {
    await keycloak.login();
  }
};