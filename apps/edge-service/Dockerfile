# Setup Image
FROM openjdk:21
LABEL authors="chris-harper"
# Run Java program
EXPOSE 8081
ADD /target/*.jar edge-service-1.0.jar
ARG PROFILE=default
ENV SPRING_PROFILE ${PROFILE}
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=${SPRING_PROFILE}", "/edge-service-1.0.jar"]