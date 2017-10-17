FROM openjdk:8-alpine AS dev

COPY . /var/plantuml-image-generator
WORKDIR /var/plantuml-image-generator
RUN ./mvnw clean package

FROM openjdk:8-alpine
LABEL maintainer="u6k.apps@gmail.com"

COPY --from=dev /var/plantuml-image-generator/target/plantuml-image-generator.jar /opt/plantuml-image-generator.jar

EXPOSE 8080

CMD ["java", "-jar", "/opt/plantuml-image-generator.jar"]
