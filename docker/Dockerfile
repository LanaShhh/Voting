FROM eclipse-temurin:17-jdk-alpine
WORKDIR /
# COPY --chmod=067 docker/generate_properties.sh generate_properties.sh
COPY application/target/voting-server-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["sh", "-c", "java -jar app.jar"]