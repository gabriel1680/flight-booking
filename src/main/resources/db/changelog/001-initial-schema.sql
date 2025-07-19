--liquibase formatted sql

--changeset yourname:001-create-flights-table
CREATE TABLE flights (
    id UUID PRIMARY KEY,
    origin VARCHAR(255),
    destination VARCHAR(255),
    capacity smallint,
    boarding_at TIMESTAMP,
    landing_at TIMESTAMP,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

--changeset yourname:002-create-flight-seats-table
CREATE TABLE flight_seats (
    id UUID PRIMARY KEY,
    flight_id UUID NOT NULL,
    number VARCHAR(6),
    type VARCHAR(50),
    availability smallint,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

--changeset yourname:003-add-foreign-key
ALTER TABLE flight_seats
ADD CONSTRAINT fk_flight_seats_flights
FOREIGN KEY (flight_id) REFERENCES flights(id);
