# Étape 1 : Construction
FROM gradle:jdk23 AS build
WORKDIR /app
COPY . .
RUN gradle build --no-daemon

# Étape 2 : Exécution
FROM eclipse-temurin:23-jre
WORKDIR /app
COPY --from=build /app/build/libs/Todo-app-spring-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]

# FROM gradle:jdk23
# WORKDIR /app
# COPY . .
# RUN gradle build --no-daemon
# ENTRYPOINT ["java", "-jar", "build/libs/Todo-app-spring-0.0.1-SNAPSHOT.jar"]



