# Stage 1: Build the application
FROM eclipse-temurin:17-jdk-alpine AS builder

RUN apk add --no-cache maven

WORKDIR /app

# Cache dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Build the actual application artifact
COPY src ./src
RUN mvn clean package -DskipTests -B

# Stage 2: Light weight production runtime
FROM eclipse-temurin:17-jre-alpine AS prod

WORKDIR /app

# To avoid wildcard issues, we target the specific standard jar structure
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]