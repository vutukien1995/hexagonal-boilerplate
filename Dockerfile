FROM eclipse-temurin:21-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file into the container
COPY target/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]