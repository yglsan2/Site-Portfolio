# Build
FROM maven:3.9-eclipse-temurin-21-alpine AS builder
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B -q

COPY src ./src
RUN mvn package -DskipTests -B -q

# Run
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Render utilise la variable PORT ; le répertoire data pour H2
ENV PORT=8080
EXPOSE 8080

COPY --from=builder /app/target/*.jar app.jar

# H2 en fichier : le répertoire doit être inscriptible
RUN mkdir -p /app/data

ENTRYPOINT ["sh", "-c", "java -Dserver.port=${PORT} -jar app.jar"]
