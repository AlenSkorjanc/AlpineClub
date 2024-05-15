FROM --platform=linux/amd64 openjdk:21-slim-bullseye
COPY target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","app.jar"]