# Kafka Connect - Connectors

In this project, is used the [debezium postgresql connector](https://debezium.io/documentation/reference/stable/connectors/postgresql.html) to retrieve the data changes from postgres and replicate it
into kafka topics.

When the kafka connect starts, it's needed to create the connector by kafka connect rest api, the documentation with
how to do it is in the main [readme file](../../../README.md)

## Connector payload

```json5
{
  "name": "admin-postgres-cdc", // the connector's name 
  "config": { // the connector's configuration
    "topic.creation.default.replication.factor": 1,
    "topic.creation.default.partitions": 1,
    "connector.class": "io.debezium.connector.postgresql.PostgresConnector",
    "plugin.name": "pgoutput", // logical decoding plugin used to read from PostgreSQL 10+
    "tasks.max": "1",
    "key.converter": "org.apache.kafka.connect.json.JsonConverter",
    "key.converter.schemas.enable": "true",
    "value.converter": "org.apache.kafka.connect.json.JsonConverter",
    "value.converter.schemas.enable": "true",
    // database info to connect
    "database.hostname": "postgres",
    "database.port": "5432",
    "database.user": "postgres",
    "database.dbname": "booking_db",
    "database.password": "123",
    "database.server.id": "10000",
    "database.server.name": "postgres",
    "database.allowPublicKeyRetrieval": "true",
    "database.include.list": "admin_flights",
    "table.include.list": "public.*", // table list that will be watched
    "database.history.kafka.bootstrap.servers": "kafka:9092", // kafka brokers
    "database.history.kafka.topic": "admin_flights.dbhistory", // stores the schema versions that may change over time
    "include.schema.changes": "false", // disable schema changes events
    "schema.enable": "false", // disables the schema object inside the payload (most used with schema registry)
    "snapshot.mode": "never", // disables events of read, keep only the mutation ones (CUD)
    "topic.prefix": "admin_flights",
    "topic.creation.admin_flights.include": "flight_db\\.admin_flights\\.*",
    "topic.creation.admin_flights.partitions": 1
  }
}
```