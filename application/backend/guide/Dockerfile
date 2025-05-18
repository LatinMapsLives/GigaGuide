# Dockerfile для guide
FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app
COPY target/guide-*.jar app.jar

EXPOSE 8083

ENTRYPOINT ["java", "-jar", "app.jar"]