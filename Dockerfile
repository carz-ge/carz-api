
FROM gradle:7.6.1-jdk17-alpine AS build
WORKDIR /workspace/app

COPY gradle gradle
COPY gradlew .
COPY settings.gradle.kts .
# RUN ./gradlew wrapper

COPY build.gradle.kts .
COPY src src

# TODO check this- > https://stackoverflow.com/questions/58593661/slow-gradle-build-in-docker-caching-gradle-build
RUN gradle bootJar -i --stacktrace --no-daemon

FROM amazoncorretto:17-alpine-jdk

COPY scripts/init.sh .
RUN chmod +x init.sh && ./init.sh

#ENV ARTIFACT_NAME=car-app-api-0.0.1-SNAPSHOT.jar
ARG BUILD_DIR=/workspace/app/build
COPY --from=build ${BUILD_DIR}/libs/ /app
EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/car-app-api-0.0.1.jar"]
#ENTRYPOINT exec java -jar ${ARTIFACT_NAME}

