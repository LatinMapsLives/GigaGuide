# Dockerfile для reviews
FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app
COPY target/reviews-*.jar app.jar

EXPOSE 8085

ENTRYPOINT ["java", "-jar", "app.jar"]