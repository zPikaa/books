FROM openjdk:17-jdk-alpine
MAINTAINER zPikaa
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]