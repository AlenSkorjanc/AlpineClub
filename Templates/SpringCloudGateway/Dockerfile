FROM openjdk:21-slim-bullseye
COPY target/*.jar app.jar
EXPOSE 8443
ENTRYPOINT ["java","-jar","app.jar"]