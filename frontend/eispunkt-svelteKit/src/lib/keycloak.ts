import Keycloak from "keycloak-js";

export const keycloak = new Keycloak({
    url: "https://it200230.cloud.htl-leonding.ac.at",
    realm: "eispunkt",
    clientId: "eispunkt-frontend"
})