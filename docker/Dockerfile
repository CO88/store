FROM gradle:8.10.2-jdk-21-and-22 as builder
WORKDIR /home/gradle/app
COPY --chown=gradle:gradle . .
RUN gradle clean bootJar --no-daemon

FROM amazoncorretto:21-alpine as runner
WORKDIR /app
COPY --from=builder /home/gradle/app/api/build/libs/*.jar app.jar
CMD ["java", "-jar", "./app.jar"]