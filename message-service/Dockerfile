FROM openjdk:8
ADD target/message-service.jar app.jar
EXPOSE 8078
ENTRYPOINT ["java", "-jar", "app.jar"]