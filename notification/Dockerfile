FROM maven:3.9.9-eclipse-temurin-17-alpine AS build
WORKDIR /app

COPY notification/pom.xml .

COPY common-exception/target/common-exception-1.0.jar /tmp/common-exception-1.0.jar

RUN --mount=type=cache,target=/root/.m2 mvn install:install-file \
      -Dfile=/tmp/common-exception-1.0.jar \
      -DgroupId=com.common \
      -DartifactId=common-exception \
      -Dversion=1.0 \
      -Dpackaging=jar

RUN --mount=type=cache,target=/root/.m2 mvn dependency:go-offline

COPY notification/src/main/resources src/main/resources
COPY notification/src/main/java src/main/java

RUN --mount=type=cache,target=/root/.m2 mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
EXPOSE 8060
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
