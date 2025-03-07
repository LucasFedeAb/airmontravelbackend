FROM openjdk:21-jdk-slim
ARG JAR_FILE=target/airmont-0.0.1.jar
COPY ${JAR_FILE} app_airmontravel.jar
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "app_airmontravel.jar" ]