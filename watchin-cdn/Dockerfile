FROM adoptopenjdk/openjdk14:debian AS builder

ENV GRADLE_OPTS -Dorg.gradle.daemon=false

WORKDIR /builder

COPY gradlew ./
COPY gradle ./gradle
RUN ./gradlew

COPY settings.gradle build.gradle ./
COPY watchin-cdn/build.gradle ./watchin-cdn/
COPY watchin-objectstorage/build.gradle ./watchin-objectstorage/
RUN ./gradlew resolveDependencies --info

COPY . .
RUN ./gradlew watchin-cdn:build -xtest --offline

RUN java -Djarmode=layertools -jar watchin-cdn/build/libs/*.jar extract

FROM adoptopenjdk/openjdk14:alpine-jre AS final

ENV SPRING_PROFILES_ACTIVE prod

WORKDIR /app

COPY --from=builder /builder/dependencies ./
RUN true
COPY --from=builder /builder/snapshot-dependencies ./
RUN true
COPY --from=builder /builder/spring-boot-loader ./
RUN true
COPY --from=builder /builder/application ./

ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
