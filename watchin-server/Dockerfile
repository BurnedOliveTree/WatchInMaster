FROM adoptopenjdk/openjdk14:debian AS builder

ENV GRADLE_OPTS -Dorg.gradle.daemon=false
ENV NODE_OPTIONS --max-old-space-size=4096

RUN curl -fsSL https://deb.nodesource.com/setup_lts.x | bash - \
    && apt-get update && apt-get install -y \
    nodejs \
    && rm -rf /var/lib/apt/lists

WORKDIR /builder

COPY gradlew ./
COPY gradle ./gradle
RUN ./gradlew

COPY settings.gradle build.gradle ./
COPY watchin-server/build.gradle ./watchin-server/
COPY watchin-objectstorage/build.gradle ./watchin-objectstorage/
COPY watchin-mailing/build.gradle ./watchin-mailing/
COPY watchin-common/build.gradle ./watchin-common/
COPY watchin-media/build.gradle ./watchin-media/
COPY watchin-queue/build.gradle ./watchin-queue/
COPY watchin-frontend/package.json ./watchin-frontend/
COPY watchin-frontend/package-lock.json ./watchin-frontend/
RUN ./gradlew resolveDependencies --info

COPY . .
RUN ./gradlew watchin-server:build -xtest --offline

RUN java -Djarmode=layertools -jar watchin-server/build/libs/*.jar extract

FROM adoptopenjdk/openjdk14:debianslim-jre AS final

ENV SPRING_PROFILES_ACTIVE prod
ENV JAVA_TOOL_OPTIONS -Djava.awt.headless=true

WORKDIR /app

COPY --from=builder /builder/dependencies ./
RUN true
COPY --from=builder /builder/snapshot-dependencies ./
RUN true
COPY --from=builder /builder/spring-boot-loader ./
RUN true
COPY --from=builder /builder/application ./

ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
