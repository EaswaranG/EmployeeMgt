# Use the official Maven image as the build stage
FROM maven:3.8.4-openjdk-8 AS build

# Set the working directory in the container
WORKDIR /app

# Copy the Maven project files to the container
COPY pom.xml .
COPY src src

# Build the Maven project
RUN mvn clean package -DskipTests

# Use the official OpenJDK 8 image as the base image for the final image
FROM openjdk:8-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the build stage to the current directory
COPY --from=build /app/target/EmployeeMgt-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your Spring Boot application will run on
EXPOSE 8080

# Command to run the Spring Boot application
CMD ["java", "-jar", "app.jar"]