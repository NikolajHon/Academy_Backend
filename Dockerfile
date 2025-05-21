
FROM maven:3.9.6-eclipse-temurin-21 AS builder
LABEL maintainer="Honcharenko Mykola" \
      version="1.0" \
      description="Dockerfile for the Spring Boot application"

WORKDIR /application

COPY pom.xml ./

COPY api-spec               api-spec/
COPY domain                 domain/
COPY inbound-controller-ws  inbound-controller-ws/
COPY outbound-repository-jpa outbound-repository-jpa/
COPY keycloak               keycloak/
COPY springboot             springboot/

RUN mvn clean package -pl springboot -am -DskipTests

# убрали слэш на конце!
RUN mkdir build \
 && cd build \
 && java -Djarmode=layertools \
      -jar ../springboot/target/*.jar extract

FROM eclipse-temurin:21.0.1_12-jdk-alpine
WORKDIR /application

COPY --from=builder /application/build/dependencies/           ./
COPY --from=builder /application/build/spring-boot-loader/    ./
COPY --from=builder /application/build/snapshot-dependencies/ ./
COPY --from=builder /application/build/application/           ./

RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

ENTRYPOINT ["sh","-c","java ${JAVA_OPTS} org.springframework.boot.loader.launch.JarLauncher"]
