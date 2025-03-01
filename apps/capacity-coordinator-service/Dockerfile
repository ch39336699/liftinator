# Setup Image
FROM openjdk:21
LABEL authors="chris-harper"
# Run Java program
EXPOSE 8082
ADD /target/*.jar capacity-coordinator-service-1.0.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=default", "/capacity-coordinator-service-1.0.jar"]