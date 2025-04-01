FROM maven:3.9.9-eclipse-temurin-17-alpine AS build
WORKDIR /

COPY team/pom.xml .

COPY common-exception/target/common-exception-1.0.jar /tmp/common-exception-1.0.jar

RUN --mount=type=cache,target=/root/.m2 mvn install:install-file \
      -Dfile=/tmp/common-exception-1.0.jar \
      -DgroupId=com.common \
      -DartifactId=common-exception \
      -Dversion=1.0 \
      -Dpackaging=jar

RUN --mount=type=cache,target=/root/.m2 mvn dependency:go-offline

COPY team/src/main/resources src/main/resources
COPY team/src/main/java src/main/java

RUN --mount=type=cache,target=/root/.m2 mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
EXPOSE 8030
COPY --from=build /target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
