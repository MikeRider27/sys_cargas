# syntax=docker/dockerfile:1.7
# ---------- build: compila la app y los reportes (.jrxml -> .jasper) ----------
FROM maven:3.9-eclipse-temurin-25-alpine AS build
WORKDIR /src
COPY pom.xml .
RUN --mount=type=cache,target=/root/.m2 mvn -B -q dependency:go-offline
COPY src ./src
RUN --mount=type=cache,target=/root/.m2 mvn -B -q package -DskipTests \
 && java -Djarmode=tools -jar target/sys-cargas.jar extract --layers --launcher --destination /app

# ---------- runtime: solo JRE, usuario sin privilegios ----------
FROM eclipse-temurin:25-jre-alpine
RUN apk upgrade --no-cache \
 && apk add --no-cache fontconfig \
 && addgroup -S app && adduser -S -G app -H -s /sbin/nologin app
WORKDIR /app
COPY --from=build /app/dependencies/ ./
COPY --from=build /app/spring-boot-loader/ ./
COPY --from=build /app/snapshot-dependencies/ ./
COPY --from=build /app/application/ ./
USER app
EXPOSE 8080
ENV JAVA_TOOL_OPTIONS="-XX:MaxRAMPercentage=75 -Djava.awt.headless=true -Djava.security.egd=file:/dev/urandom"
HEALTHCHECK --interval=15s --timeout=5s --start-period=60s --retries=5 \
  CMD wget -qO- http://127.0.0.1:8080/actuator/health | grep -q '"UP"' || exit 1
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
