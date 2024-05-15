FROM --platform=linux/amd64 eclipse-temurin:21.0.3_9-jre-ubi9-minimal
COPY target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","app.jar"]