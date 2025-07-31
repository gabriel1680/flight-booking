# Booking Application

This is a flight booking application, created in order to exercise
patterns and concepts such as hexagonal architecture, domain driven design
event driven, CQS and modular monoliths with clear boundaries. 

## The problem

In most concurrent applications, such this one, the common problem is the
relation between concurrency and consistency. In this example I've tried
to exercise the solution for it + the simplest way to solve the increasing
complexity of such system.

## Core concepts

- Domain Driven Design
- Modular monoliths
- CQRS
- Event Driven Architecture
- Hexagonal Architecture
- Distributed Concurrency

## Tech Stack

- Java (with Spring)
- Postgres
- Kafka
- Kafka Connect
- RabbitMQ
- Elasticsearch
- Docker

## Infrastructure

- Kafka Broker - `localhost:9092`
- Zookeeper - `localhost:2181`
- Postgres - `localhost:5432`
- Debezium Connector - `localhost:8083`
- Schema Registry - `localhost:8081`
- Control Center - `localhost:9021`

## Configuration

### Development

Startup containers

```shell
docker compose up -d
```

Configure kafka connect connector with debezium

```shell
curl --location --request POST 'http://localhost:8083/connectors' \
--header 'Content-Type: application/json' \
--data '{
	"name": "admin-postgres-cdc",
		"config": {
		"topic.creation.default.replication.factor": 1,
		"topic.creation.default.partitions": 1,
		"connector.class": "io.debezium.connector.postgresql.PostgresConnector",
		"plugin.name": "pgoutput",
		"tasks.max": "1",
		"key.converter": "org.apache.kafka.connect.json.JsonConverter",
		"key.converter.schemas.enable": "true",
		"value.converter": "org.apache.kafka.connect.json.JsonConverter",
		"value.converter.schemas.enable": "true",
		"database.hostname": "postgres",
		"database.port": "5432",
		"database.user": "postgres",
		"database.dbname": "booking_db",
		"database.password": "123",
		"database.server.id": "10000",
		"database.server.name": "postgres",
		"database.allowPublicKeyRetrieval": "true",
		"database.include.list": "admin_flights",
		"table.include.list": "public.*",
		"database.history.kafka.bootstrap.servers": "kafka:9092",
		"database.history.kafka.topic": "admin_flights.dbhistory",
		"include.schema.changes": "false",
		"schema.enable": "false",
		"topic.prefix": "admin_flights",
		"topic.creation.admin_flights.include": "flight_db\\.admin_flights\\.*",
		"topic.creation.admin_flights.partitions": 1
	}
}'
```