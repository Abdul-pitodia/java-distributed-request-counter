FROM openjdk:17-slim as builder

WORKDIR /app

COPY target/*.jar app.jar


FROM openjdk:17-slim

WORKDIR /app

COPY --from=builder /app/app.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
