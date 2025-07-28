FROM eclipse-temurin:21-jdk as build
WORKDIR /workspace/app

COPY gradle gradle
COPY gradlew gradlew.bat ./
COPY build.gradle settings.gradle ./
COPY src src

RUN ./gradlew bootJar -x test

FROM eclipse-temurin:21-jre
VOLUME /tmp

COPY --from=build /workspace/app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
