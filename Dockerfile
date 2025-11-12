# Use a Maven image that includes a JDK (e.g., OpenJDK 17)
FROM maven:3.8-openjdk-17

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml first to cache the dependency download layer
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the rest of your source code
COPY src ./src

# Run the build (as you requested)
# This will compile your classes, including the Lombok processing
RUN mvn clean install

# Expose the port your SparkJava app listens on (default is 4567)
EXPOSE 4567

# This is the command that will run when the container starts
CMD ["mvn", "exec:java"]
