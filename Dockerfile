# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the application JAR file to the container
COPY target/*.jar exo6-0.0.1-SNAPSHOT.jar

# Run the application
ENTRYPOINT ["java", "-jar", "exo6-0.0.1-SNAPSHOT.jar"]
