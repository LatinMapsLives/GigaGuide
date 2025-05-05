# Dockerfile для tour-sight
FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app
COPY ../target/tour-sight-*.jar app.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "app.jar"]