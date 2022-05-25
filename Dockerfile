FROM adoptopenjdk/openjdk11:latest
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
HEALTHCHECK CMD curl --fail http://localhost:8080/food/actuator/health || exit 1

ENTRYPOINT ["java","-jar","/app.jar"]
