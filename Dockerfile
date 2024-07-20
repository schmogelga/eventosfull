FROM openjdk:17-jdk-slim

WORKDIR /app

COPY build/libs/eventosfull-1.1.0-SNAPSHOT.jar /app/eventosfull.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/eventosfull.jar"]
