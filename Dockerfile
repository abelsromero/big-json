FROM gcr.io/distroless/java:11

ADD target/app.jar app.jar

ENTRYPOINT ["java", \
            "-jar", \
            "/app.jar"]
EXPOSE 8080
