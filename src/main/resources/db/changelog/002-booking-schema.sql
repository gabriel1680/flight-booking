--liquibase formatted sql

--changeset yourname:001-create-flights-table
CREATE TABLE IF NOT EXISTS bookings (
    id UUID PRIMARY KEY,
    flight_id UUID NOT NULL,
    email VARCHAR(255),
    status VARCHAR(50),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

--changeset yourname:002-create-flight-seats-table
CREATE TABLE IF NOT EXISTS booking_seats (
    id UUID PRIMARY KEY,
    booking_id UUID NOT NULL,
    price float,
    type VARCHAR(50),
    availability smallint,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

--changeset yourname:003-add-foreign-key
ALTER TABLE flight_seats
ADD CONSTRAINT fk_flight_seats_flights
FOREIGN KEY (flight_id) REFERENCES flights(id);
