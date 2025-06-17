FROM openjdk:17-jdk-slim

WORKDIR /app

COPY build/libs/*.jar /app/app.jar

ENV JAVA_OPTS=""

EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
