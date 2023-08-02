# Use a base image with Java 8
FROM openjdk:8-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the Java application JAR file into the container
COPY target/EmployeeMgt.jar /app/EmployeeMgt.jar

# Expose the port that the application will run on
EXPOSE 8080

# Define the command to run your Java application
CMD ["java", "-jar", "EmployeeMgt.jar"]
