FROM openjdk:11.0-slim
EXPOSE 8080
COPY /build/libs/polling-session-manager-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]