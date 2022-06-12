FROM gradle:7-jdk17 AS build

COPY --chown=gradle:gradle . /home/gradle/src

WORKDIR /home/gradle/src

RUN gradle shadowJar --no-daemon


FROM openjdk:17

EXPOSE 8080:8080

RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*-all.jar /app/ktor-fake-api.jar

RUN mkdir /data
COPY src/main/kotlin/io/github/dawidl022/data /data

ENTRYPOINT ["java","-jar","/app/ktor-fake-api.jar"]
