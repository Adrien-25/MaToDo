FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY build/libs/Todo-app-spring-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

# FROM gradle:7.2-jdk17 AS build
# WORKDIR /app
# COPY . .
# RUN gradle build --no-daemon

# FROM openjdk:17-jdk-slim
# WORKDIR /app
# COPY --from=build /app/build/libs/*.jar app.jar
# ENTRYPOINT ["java","-jar","app.jar"]