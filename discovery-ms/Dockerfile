FROM openjdk:17
LABEL maintainer="Gabriel Poersch"
COPY ./discovery-ms/target/*.jar app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "app.jar"]
EXPOSE 8761