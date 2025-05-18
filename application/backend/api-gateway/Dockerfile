# Dockerfile для api-gateway
FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app
COPY target/api-gateway-*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]