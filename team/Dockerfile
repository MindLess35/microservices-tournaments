FROM maven:3.9.9-eclipse-temurin-17-alpine AS build
WORKDIR /

COPY pom.xml .
#
#COPY libs/common-starter-exception-1.0-SNAPSHOT.jar /tmp/
#
#RUN mvn install:install-file \
#      -Dfile=/tmp/common-starter-exception-1.0-SNAPSHOT.jar \
#      -DgroupId=com.common \
#      -DartifactId=common-starter-exception \
#      -Dversion=1.0-SNAPSHOT \
#      -Dpackaging=jar \

RUN --mount=type=cache,target=/root/.m2 mvn dependency:go-offline

COPY src/main/resources src/main/resources
COPY src/main/java src/main/java

RUN --mount=type=cache,target=/root/.m2 mvn clean package -DskipTests


FROM eclipse-temurin:17-jre-alpine
EXPOSE 8030
COPY --from=build /target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]