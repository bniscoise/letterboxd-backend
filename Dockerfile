# Étape 1 : Build du projet Java avec Maven
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copier les fichiers de build Maven
COPY pom.xml .
COPY src ./src

# Compiler et packager le projet sans les tests
RUN mvn -DskipTests clean package

# Étape 2 : Exécuter le JAR dans un JDK léger
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copier le JAR depuis l'étape précédente
COPY --from=build /app/target/*.jar app.jar

# Exposer le port que Render assignera
EXPOSE 8080

# Démarrer l'application (Render injecte $PORT automatiquement)
CMD ["java", "-Dserver.port=${PORT}", "-jar", "app.jar"]