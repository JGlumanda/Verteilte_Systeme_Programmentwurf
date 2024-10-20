FROM gradle:7.5.1-jdk17 as builder

WORKDIR /app

COPY build.gradle.kts settings.gradle.kts gradlew /app/

COPY gradle /app/gradle

RUN ./gradlew dependencies

COPY src /app/src

RUN ./gradlew build bootJar

FROM openjdk:17-slim as runtime

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080 5005

ENV JAVA_TOOL_OPTIONS="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"

ENTRYPOINT ["java", "-jar", "app.jar"]
