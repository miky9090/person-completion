FROM openjdk:9-jdk
EXPOSE 9090
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
COPY kodszotar46_okmanytipus.json kodszotar46_okmanytipus.json
ENTRYPOINT ["java","-Dspring.profiles.active=discovery","-jar","/app.jar"]