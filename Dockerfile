FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY ./build/libs/booking-0.0.1-SNAPSHOT.jar ./app.jar

# Download or copy OpenTelemetry Java agent
RUN curl -L -o opentelemetry-javaagent.jar https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/latest/download/opentelemetry-javaagent.jar

# Default environment vars (can be overridden in docker-compose)
ENV OTEL_SERVICE_NAME=booking-app

ENV JAVA_TOOL_OPTIONS="-javaagent:/app/opentelemetry-javaagent.jar"

# Run the app with the agent
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
