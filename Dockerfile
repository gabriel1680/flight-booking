FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY ./build/libs/booking-0.0.1-SNAPSHOT.jar ./app.jar

# Download or copy OpenTelemetry Java agent
RUN curl -L -o opentelemetry-javaagent.jar https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v1.38.0/opentelemetry-javaagent.jar

# Default environment vars (can be overridden in docker-compose)
ENV OTEL_SERVICE_NAME=booking-app \
    OTEL_EXPORTER_OTLP_ENDPOINT=http://otel-collector:4317 \
    OTEL_METRICS_EXPORTER=otlp \
    OTEL_LOGS_EXPORTER=none

# Run the app with the agent
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
