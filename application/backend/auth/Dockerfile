# Dockerfile для auth
FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app
COPY target/auth-*.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]