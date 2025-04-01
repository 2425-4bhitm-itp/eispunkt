FROM eclipse-temurin:21-jre-jammy
RUN mkdir -p /opt/eispunkt
WORKDIR /opt/eispunkt
COPY ./target/*-runner.jar ./quarkus-run.jar

CMD ["java", "-jar","quarkus-run.jar"]
