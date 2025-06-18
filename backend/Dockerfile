# -------- Build Stage --------
FROM maven:3.8.6-eclipse-temurin-19 AS builder

WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the rest of the project
COPY src ./src

# Build the application (skip tests)
RUN mvn clean package -DskipTests

# -------- Runtime Stage --------
FROM amazoncorretto:19

WORKDIR /app

# Copy only the compiled jar from the build stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the port your app listens on (adjust if needed)
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
