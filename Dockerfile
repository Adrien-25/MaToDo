FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY build/libs/Todo-app-spring-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

# FROM openjdk:17-jdk-slim

# # Ajouter Gradle pour construire l'application
# # RUN apt-get update && apt-get install -y gradle

# # Copier tout le projet
# COPY . /app

# # Aller dans le répertoire de l'application
# WORKDIR /app

# # Construire le fichier JAR
# RUN ./gradlew build

# # Copier le fichier JAR généré
# COPY build/libs/*.jar app.jar

# # Définir le point d'entrée
# ENTRYPOINT ["java", "-jar", "/app.jar"]
