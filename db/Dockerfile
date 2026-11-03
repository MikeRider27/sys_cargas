# Postgres oficial endurecido: parches de Alpine al dia y sin gosu (binario Go
# con CVEs). El contenedor arranca directamente como usuario postgres, por lo
# que el entrypoint oficial no necesita gosu para bajar privilegios.
FROM postgres:18-alpine
RUN apk upgrade --no-cache && rm -f /usr/local/bin/gosu
USER postgres
