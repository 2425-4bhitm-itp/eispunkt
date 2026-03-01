import { browser } from "$app/environment"
import { keycloak } from "$lib/keycloak"

export const load = async() => {
    if(!browser) return {};

    await keycloak.init({
        onLoad: "check-sso",
        silentCheckSsoRedirectUri: `${window.location.origin}/silent-check-sso.html`
    })

    if (keycloak.authenticated) {
    await fetch('/api/users/init', {
      method: 'POST',
      headers: { Authorization: `Bearer ${keycloak.token}` }
    });
  }

    return {authenticated: keycloak.authenticated};
}