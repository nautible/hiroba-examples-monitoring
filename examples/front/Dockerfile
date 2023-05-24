FROM openjdk:18-jdk-alpine AS build
COPY . /home/gradle/src
WORKDIR /home/gradle/src

#RUN wget https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/latest/download/opentelemetry-javaagent.jar
RUN mkdir -p ~/.gradle && cp env/gradle.properties ~/.gradle/gradle.properties && \
    ./gradlew clean build -x test --no-daemon 

FROM openjdk:18-jdk-slim

EXPOSE 8080

RUN mkdir /app

COPY --from=build /home/gradle/src/build/libs/springboot3-*-SNAPSHOT.jar /app/spring-boot-application.jar
#COPY --from=build /home/gradle/src/opentelemetry-javaagent.jar /app/opentelemetry-javaagent.jar
COPY lib/opentelemetry-javaagent.jar /app/opentelemetry-javaagent.jar

#ENV JAVA_OPTS="-javaagent:/app/opentelemetry-javaagent.jar"
ENTRYPOINT ["java", "-javaagent:/app/opentelemetry-javaagent.jar", "-jar","/app/spring-boot-application.jar"]
