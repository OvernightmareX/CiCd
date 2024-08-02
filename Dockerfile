# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the application JAR file to the container
COPY target/.jar app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
