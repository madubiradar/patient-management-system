FROM gradle:8.5-jdk21 AS builder

WORKDIR /app

COPY build.gradle .
RUN gradle dependencies --no-daemon

COPY src ./src

# Build the application
RUN gradle clean build --no-daemon

FROM openjdk:21-jdk AS runner

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar ./app.jar

EXPOSE 3000

ENTRYPOINT ["java", "-jar", "app.jar"]

LABEL authors="madubiradar"
