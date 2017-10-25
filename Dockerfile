FROM openjdk:8-alpine AS dev

# Install japanese font
RUN apk update && \
    apk upgrade && \
    apk --no-cache add \
        fontconfig \
        graphviz && \
    mkdir -p ~/.fonts && \
    cd ~/.fonts && \
    wget -O ipagp00303.zip http://ipafont.ipa.go.jp/old/ipafont/ipagp00303.php && \
    unzip ipagp00303.zip && \
    rm ipagp00303.zip && \
    fc-cache -fv

# Build application
COPY . /var/plantuml-image-generator
WORKDIR /var/plantuml-image-generator
RUN ./mvnw clean package

FROM openjdk:8-alpine
LABEL maintainer="u6k.apps@gmail.com"

# Copy package
COPY --from=dev /var/plantuml-image-generator/target/plantuml-image-generator.jar /opt/plantuml-image-generator.jar

# Install japanese font
RUN apk update && \
    apk upgrade && \
    apk --no-cache add \
        fontconfig \
        graphviz && \
    mkdir -p ~/.fonts && \
    cd ~/.fonts && \
    wget -O ipagp00303.zip http://ipafont.ipa.go.jp/old/ipafont/ipagp00303.php && \
    unzip ipagp00303.zip && \
    rm ipagp00303.zip && \
    fc-cache -fv

# Setting
EXPOSE 8080

CMD ["java", "-jar", "/opt/plantuml-image-generator.jar"]
