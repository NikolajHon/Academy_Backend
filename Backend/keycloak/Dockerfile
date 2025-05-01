FROM quay.io/keycloak/keycloak:26.0.7

COPY realm-fsa.json /opt/keycloak/data/import/realm.json
COPY keycloak-providers/ /opt/keycloak/providers/

ENTRYPOINT ["/opt/keycloak/bin/kc.sh", "start-dev", "--import-realm"]
