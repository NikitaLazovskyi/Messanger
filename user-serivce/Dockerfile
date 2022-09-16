FROM openjdk:8
ADD target/user-service.jar app.jar
EXPOSE 8087
ENTRYPOINT ["java", "-jar", "app.jar"]