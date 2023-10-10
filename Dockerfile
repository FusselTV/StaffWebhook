# Use openjdk image version 19 as the base image
FROM eclipse-temurin:19-jre-alpine

# Update the packages in the image
RUN apk update && apk upgrade

# Set the working directory
WORKDIR /app

# Copy the JDA bot code to the working directory
COPY ./build/libs/StaffWebhook.jar .

# Run the JDA bot jar file, passing in the environment variables as command line arguments
CMD ["java", "-jar", "StaffWebhook.jar"]

# Set the user and group to non-root
USER 1001:0