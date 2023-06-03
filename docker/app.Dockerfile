FROM eclipse-temurin:17-jdk-alpine
WORKDIR /
EXPOSE 8080
COPY application/target/voting-server-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["sh", "-c", "java -jar app.jar"]