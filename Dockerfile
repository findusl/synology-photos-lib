# Stage 1: Build the JAR.
# This has gradle already downloaded. Just jdk container could use the wrapper it would download then
FROM gradle:8.10.0-jdk21 AS builder

# Set the working directory
WORKDIR /app

# Copy the entire project into the container
COPY . .

# Run the Gradle task to build the fat JAR
RUN gradle buildFatJar

# Stage 2: Run the application
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the fat JAR from the builder stage
COPY --from=builder /app/server/build/libs/server-all.jar app.jar

# Create logs directory
RUN mkdir -p /app/logs

# Specify the command to run the JAR file
CMD ["java", "-jar", "app.jar"]
