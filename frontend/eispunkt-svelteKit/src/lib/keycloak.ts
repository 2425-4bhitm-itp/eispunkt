import Keycloak from 'keycloak-js';

export const keycloak = new Keycloak({
  url: 'https://it200230.cloud.htl-leonding.ac.at',
  realm: 'eispunkt',
  clientId: 'eispunkt-frontend',
});

let initPromise: Promise<boolean> | null = null;

export function initKeycloak(): Promise<boolean> {
  if (!initPromise) {
    initPromise = keycloak.init({
      onLoad: 'check-sso',
      silentCheckSsoRedirectUri: `${window.location.origin}/silent-check-sso.html`,
      checkLoginIframe: false
    });
  }
  return initPromise;
}