FROM adoptopenjdk/openjdk15:jdk-15.0.1_9-alpine-slim as build
RUN mkdir -p /usr/share/build
WORKDIR /usr/share/build
ADD target/rd-neo4j-streaming-0.0.1-SNAPSHOT.jar .

FROM adoptopenjdk/openjdk15:jdk-15.0.1_9-alpine
COPY --from=build /usr/share/build/rd-neo4j-streaming-0.0.1-SNAPSHOT.jar /app.jar


ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]