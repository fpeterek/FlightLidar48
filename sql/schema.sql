-- Country

CREATE TABLE country (
    id SERIAL NOT NULL,
    country_name VARCHAR(100) NOT NULL,
    airport_prefix VARCHAR(2) NOT NULL,
    registration_prefix VARCHAR(2) NOT NULL
);

ALTER TABLE country ADD CONSTRAINT country_pk PRIMARY KEY (id);


-- Airport

CREATE TABLE airport (
    icao CHAR(4) NOT NULL,
    iata CHAR(3) NOT NULL,
    airport_name VARCHAR(100) NOT NULL,
    lat FLOAT NOT NULL,
    lon FLOAT NOT NULL,
    country_id INTEGER NOT NULL
);

ALTER TABLE airport ADD CONSTRAINT airport_pk PRIMARY KEY (icao);


-- CurrentFlight

CREATE TABLE current_flight (
    id SERIAL NOT NULL,
    flight INTEGER NOT NULL,
    lat FLOAT,
    lon FLOAT,
    squawk INTEGER,
    altitude INTEGER,
    direction FLOAT,
    groundspeed INTEGER
);

ALTER TABLE current_flight ADD CONSTRAINT current_flight_pk PRIMARY KEY (id);


-- Flight

CREATE TABLE flight (
    id SERIAL NOT NULL,
    number VARCHAR(10),
    departure TIMESTAMP,
    arrival TIMESTAMP,
    origin CHAR(4) NOT NULL,
    destination CHAR(4) NOT NULL,
    aircraft CHAR(6) NOT NULL,
    current_flight INTEGER
);

ALTER TABLE flight ADD CONSTRAINT flight_pk PRIMARY KEY (id);


-- AircraftType

CREATE TABLE aircraft_type (
    designator VARCHAR(10) NOT NULL,
    manufacturer VARCHAR(100) NOT NULL,
    family VARCHAR(100) NOT NULL,
    subtype VARCHAR(100) NOT NULL
);

ALTER TABLE aircraft_type ADD CONSTRAINT aircraft_type_pk PRIMARY KEY (designator);


-- Airline

CREATE TABLE airline (
    designator CHAR(2) NOT NULL,
    airline_name VARCHAR(100)
);

ALTER TABLE airline ADD CONSTRAINT airline_pk PRIMARY KEY (designator);


-- Aircraft

CREATE TABLE aircraft (
    registration CHAR(6) NOT NULL,
    msn INTEGER NOT NULL,
    airline CHAR(2) NOT NULL,
    type_designator VARCHAR(10) NOT NULL,
    first_flight TIMESTAMP
);

ALTER TABLE aircraft ADD CONSTRAINT aircraft_pk PRIMARY KEY (registration);


-- Foreign Keys

ALTER TABLE airport ADD CONSTRAINT country_id_fk FOREIGN KEY (country_id) REFERENCES country(id);

ALTER TABLE current_flight ADD CONSTRAINT current_flight_flight_id_pk FOREIGN KEY (flight) REFERENCES flight(id);

ALTER TABLE flight ADD CONSTRAINT flight_origin_pk FOREIGN KEY (origin) REFERENCES airport(icao);
ALTER TABLE flight ADD CONSTRAINT flight_destination_pk FOREIGN KEY (destination) REFERENCES airport(icao);
ALTER TABLE flight ADD CONSTRAINT flight_aircraft_pk FOREIGN KEY (aircraft) REFERENCES aircraft(registration);
ALTER TABLE flight ADD CONSTRAINT flight_current_id_pk FOREIGN KEY (current_flight) REFERENCES current_flight(id) DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE aircraft ADD CONSTRAINT aircraft_airline_pk FOREIGN KEY (airline) REFERENCES airline(designator);
