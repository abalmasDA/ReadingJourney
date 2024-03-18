FROM openjdk:21
EXPOSE 8080
ENV JAR_FILE=target/ReadingJourney-1.0-SNAPSHOT.jar
COPY ${JAR_FILE} /ReadingJourney.jar
ENTRYPOINT ["java", "-jar", "/ReadingJourney.jar"]
