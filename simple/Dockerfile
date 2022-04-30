FROM openjdk:8-jdk

RUN apt-get update && apt-get -y install sudo

ARG JAR_FILE="build/libs/simple-*.jar"

COPY ${JAR_FILE} app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app.jar"]