FROM eclipse-temurin:17-jdk-alpine
WORKDIR /
COPY target/voting-server-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["sh", "-c", "java -jar app.jar"]