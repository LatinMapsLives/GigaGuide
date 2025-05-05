# Dockerfile для map
FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app
COPY target/map-*.jar app.jar

EXPOSE 8086

ENTRYPOINT ["java", "-jar", "app.jar"]