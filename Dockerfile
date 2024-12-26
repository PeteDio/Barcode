# Stage 1: Build the Maven project
FROM maven:3.9.9-amazoncorretto-23-debian-bookworm AS build-stage
WORKDIR /app

# Copy only the necessary files
COPY pom.xml .
COPY src/main/java/ /app/src/main/java/

# Cache dependencies and build the project
RUN mvn dependency:go-offline
RUN mvn package -DskipTests

# Stage 2: Create the final image
FROM openjdk:latest AS run-stage
WORKDIR /app

# Copy only the final jar file from the build stage
COPY --from=build-stage /app/target/*.jar app.jar

# Expose the port for the application
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]