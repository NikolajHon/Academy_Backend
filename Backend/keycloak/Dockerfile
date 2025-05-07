FROM quay.io/keycloak/keycloak:23.0

# Импорт realm и custom-провайдеров
COPY realm-fsa.json /opt/keycloak/data/import/realm.json
COPY keycloak-providers/ /opt/keycloak/providers/

ENTRYPOINT ["/opt/keycloak/bin/kc.sh", "start", "--import-realm", "--optimized"]
