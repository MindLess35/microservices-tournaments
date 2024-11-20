FROM maven:3.9.9-eclipse-temurin-17-alpine AS build
WORKDIR /

COPY pom.xml .

RUN --mount=type=cache,target=/root/.m2 mvn dependency:go-offline

COPY src/main/resources src/main/resources
COPY src/main/java src/main/java

RUN --mount=type=cache,target=/root/.m2 mvn clean package


FROM eclipse-temurin:17-jre-alpine
COPY --from=build /target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]