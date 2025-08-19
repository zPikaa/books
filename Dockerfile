FROM openjdk:17-jdk-alpine

LABEL maintainer="zPikaa"

WORKDIR /app

COPY target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]