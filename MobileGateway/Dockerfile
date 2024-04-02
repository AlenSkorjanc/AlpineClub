FROM openjdk:21-slim-bullseye
COPY target/*.jar app.jar
EXPOSE 8085
ENTRYPOINT ["java","-jar","app.jar"]