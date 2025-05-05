# Dockerfile для tour-sight
FROM eclipse-temurin:21-jdk-jammy

WORKDIR /tour-sight
COPY target/tour-sight-*.jar tour-sight.jar
COPY images /tour-sight/images

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "tour-sight.jar"]