# Phase 1: Build the application
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY src ./src
COPY pom.xml .
RUN mvn clean package -DskipTests

# Phase 2: Create a startup image
FROM openjdk:21
EXPOSE 8080
ENV JAR_FILE=target/ReadingJourney-1.0-SNAPSHOT.jar
COPY --from=build /app/${JAR_FILE} /ReadingJourney.jar
ENTRYPOINT ["java", "-jar", "/ReadingJourney.jar"]
