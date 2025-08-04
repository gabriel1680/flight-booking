package org.gbl.kernel.infra.jackson;

import com.fasterxml.jackson.core.type.TypeReference;
import org.gbl.booking.event.BookingCreated;
import org.gbl.catalog.CatalogDto.FlightDto;
import org.gbl.catalog.in.kafka.util.MessageValue;
import org.gbl.catalog.in.kafka.util.Operation;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class JacksonJsonParserTest {

    @Nested
    class KafkaConnectPayloadParsingShould {

        private static final String JSON = """
                {"schema":{"type":"struct","fields":[{"type":"struct","fields":[{"type":"string","optional":false,"name":"io.debezium.data.Uuid","version":1,"field":"id"},{"type":"string","optional":true,"name":"io.debezium.time.ZonedTimestamp","version":1,"field":"boarding_at"},{"type":"int16","optional":true,"field":"capacity"},{"type":"string","optional":true,"name":"io.debezium.time.ZonedTimestamp","version":1,"field":"created_at"},{"type":"string","optional":true,"field":"destination"},{"type":"string","optional":true,"name":"io.debezium.time.ZonedTimestamp","version":1,"field":"landing_at"},{"type":"string","optional":true,"field":"origin"},{"type":"string","optional":true,"name":"io.debezium.time.ZonedTimestamp","version":1,"field":"updated_at"}],"optional":true,"name":"admin_flights.public.flights.Value","field":"before"},{"type":"struct","fields":[{"type":"string","optional":false,"name":"io.debezium.data.Uuid","version":1,"field":"id"},{"type":"string","optional":true,"name":"io.debezium.time.ZonedTimestamp","version":1,"field":"boarding_at"},{"type":"int16","optional":true,"field":"capacity"},{"type":"string","optional":true,"name":"io.debezium.time.ZonedTimestamp","version":1,"field":"created_at"},{"type":"string","optional":true,"field":"destination"},{"type":"string","optional":true,"name":"io.debezium.time.ZonedTimestamp","version":1,"field":"landing_at"},{"type":"string","optional":true,"field":"origin"},{"type":"string","optional":true,"name":"io.debezium.time.ZonedTimestamp","version":1,"field":"updated_at"}],"optional":true,"name":"admin_flights.public.flights.Value","field":"after"},{"type":"struct","fields":[{"type":"string","optional":false,"field":"version"},{"type":"string","optional":false,"field":"connector"},{"type":"string","optional":false,"field":"name"},{"type":"int64","optional":false,"field":"ts_ms"},{"type":"string","optional":true,"name":"io.debezium.data.Enum","version":1,"parameters":{"allowed":"true,last,false,incremental"},"default":"false","field":"snapshot"},{"type":"string","optional":false,"field":"db"},{"type":"string","optional":true,"field":"sequence"},{"type":"int64","optional":true,"field":"ts_us"},{"type":"int64","optional":true,"field":"ts_ns"},{"type":"string","optional":false,"field":"schema"},{"type":"string","optional":false,"field":"table"},{"type":"int64","optional":true,"field":"txId"},{"type":"int64","optional":true,"field":"lsn"},{"type":"int64","optional":true,"field":"xmin"}],"optional":false,"name":"io.debezium.connector.postgresql.Source","field":"source"},{"type":"struct","fields":[{"type":"string","optional":false,"field":"id"},{"type":"int64","optional":false,"field":"total_order"},{"type":"int64","optional":false,"field":"data_collection_order"}],"optional":true,"name":"event.block","version":1,"field":"transaction"},{"type":"string","optional":false,"field":"op"},{"type":"int64","optional":true,"field":"ts_ms"},{"type":"int64","optional":true,"field":"ts_us"},{"type":"int64","optional":true,"field":"ts_ns"}],"optional":false,"name":"admin_flights.public.flights.Envelope","version":2},"payload":{"before":null,"after":{"id":"855ac320-1067-4d99-b4f1-6d46c64c514f","boarding_at":"2025-07-16T18:10:13.467000Z","capacity":2,"created_at":null,"destination":"Nossa","landing_at":"2025-07-19T18:10:13.467000Z","origin":"Minha casa","updated_at":"2025-07-30T18:36:58.596117Z"},"source":{"version":"3.0.0.Final","connector":"postgresql","name":"admin_flights","ts_ms":1753908214454,"snapshot":"true","db":"booking_db","sequence":"[null,\\"26834688\\"]","ts_us":1753908214454091,"ts_ns":1753908214454091000,"schema":"public","table":"flights","txId":777,"lsn":26834688,"xmin":null},"transaction":null,"op":"r","ts_ms":1753908214565,"ts_us":1753908214565935,"ts_ns":1753908214565935173}}""";
        private static final TypeReference<MessageValue<FlightDto>> TYPE = new TypeReference<>() {};

        @Test
        void parseJson() {
            assertNotNull(JacksonJsonParser.parse(JSON, TYPE));
        }

        @Test
        void parseEvent() {
            var json = "{\"bookingId\":\"f175d574-fc21-43bf-809f-6c5a259f22c4\",\"flightId\":\"8b1c7e1f-abb7-4910-bdad-403589ee44b4\",\"seatIds\":[\"9420882a-108e-4e88-a46b-110de0ad63e7\"]}";
            final var actual = JacksonJsonParser.parse(json, BookingCreated.class);
            assertNotNull(actual);
            assertNotNull(actual.bookingId());
        }

        @Test
        void getPayload() {
            final var parsed = JacksonJsonParser.parse(JSON, TYPE);
            assertNotNull(parsed.payload());
        }

        @Test
        void getPayloadOperationAndAfter() {
            final var parsed = JacksonJsonParser.parse(JSON, TYPE);
            final var payload = parsed.payload();
            assertInstanceOf(FlightDto.class, payload.after());
            assertEquals(Operation.READ, payload.operation());
        }
    }
}