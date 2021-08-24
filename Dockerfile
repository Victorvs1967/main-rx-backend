FROM adoptopenjdk/openjdk11:ubi

ADD . /app
WORKDIR /app
RUN ./mvnw package -DskipTests
EXPOSE 8080
ENTRYPOINT ["java","-jar","target/main-rx-backend-0.0.1-SNAPSHOT.jar"]