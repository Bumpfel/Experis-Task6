FROM openjdk:14
VOLUME tmp
ADD target/task6-herokunate-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]