# Use a Gradle base image
FROM gradle:8.10.2-jdk17 AS build

# Set the working directory in the container
WORKDIR /app

# Copy the project files into the container
COPY . .

# Build the project using Gradle
RUN gradle build

# Use a smaller image for the final runtime
FROM openjdk:17-jdk-slim

# Copy the built JAR file from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose the port your application listens on (if applicable)
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]