# FROM openjdk:23-jdk-slim
# VOLUME /tmp
# COPY build/libs/Todo-app-spring-0.0.1-SNAPSHOT.jar app.jar
# ENTRYPOINT ["java","-jar","/app.jar"]



# Étape 1 : Construction
FROM gradle:jdk23 AS build
WORKDIR /app
COPY . .
RUN gradle build --no-daemon

# Étape 2 : Exécution
FROM openjdk:23-jdk-slim
WORKDIR /app
COPY --from=build /app/build/libs/Todo-app-spring-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
