FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/final-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar", "--spring.config.location=classpath:/application.yml"]
