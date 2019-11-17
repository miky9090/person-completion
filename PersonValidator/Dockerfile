FROM openjdk:9-jdk
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
COPY kodszotar21_allampolg.json kodszotar21_allampolg.json
ENTRYPOINT ["java","-Dspring.profiles.active=discovery","-jar","/app.jar"]