INSERT INTO country (country_name, airport_prefix, registration_prefix) VALUES ('United States of America', 'K', 'N');
INSERT INTO country (country_name, airport_prefix, registration_prefix) VALUES ('Canada', 'C', 'C');
INSERT INTO country (country_name, airport_prefix, registration_prefix) VALUES ('Germany', 'ED', 'D');
INSERT INTO country (country_name, airport_prefix, registration_prefix) VALUES ('Great Britain', 'EG', 'G');
INSERT INTO country (country_name, airport_prefix, registration_prefix) VALUES ('France', 'LF', 'F');
INSERT INTO country (country_name, airport_prefix, registration_prefix) VALUES ('Switzerland', 'LS', 'HB');
INSERT INTO country (country_name, airport_prefix, registration_prefix) VALUES ('Czech Republic', 'LK', 'OK');
INSERT INTO country (country_name, airport_prefix, registration_prefix) VALUES ('Singapore', 'WS', '9V');
INSERT INTO country (country_name, airport_prefix, registration_prefix) VALUES ('United Arab Emirates', 'OM', 'A6');

INSERT INTO airport (icao, iata, airport_name, lat, lon, country_id) VALUES('KLAX', 'LAX', 'Los Angeles International Airport', -118.408, 33.9425, 1);
INSERT INTO airport (icao, iata, airport_name, lat, lon, country_id) VALUES('KJFK', 'JFK', 'John F. Kennedy International Airport', -73.7789, 40.64, 1);
INSERT INTO airport (icao, iata, airport_name, lat, lon, country_id) VALUES('KATL', 'ATL', 'Hartsfield–Jackson Atlanta International Airport', -84.4281, 33.6367, 1);
INSERT INTO airport (icao, iata, airport_name, lat, lon, country_id) VALUES('CYYZ', 'YYZ', 'Toronto Pearson International Airport', -79.6306, 43.6767, 2);
INSERT INTO airport (icao, iata, airport_name, lat, lon, country_id) VALUES('EDDF', 'FRA', 'Frankfurt Airport', 8.5706, 50.0333, 3);
INSERT INTO airport (icao, iata, airport_name, lat, lon, country_id) VALUES('EDDM', 'MUC', 'Munich Airport', 11.7861, 48.3539, 3);
INSERT INTO airport (icao, iata, airport_name, lat, lon, country_id) VALUES('EGLL', 'LHR', 'Heathrow Airport', -0.4614, 51.4775, 4);
INSERT INTO airport (icao, iata, airport_name, lat, lon, country_id) VALUES('LFPG', 'CDG', 'Paris Charles de Gaulle Airport', 2.5478, 49.0097, 5);
INSERT INTO airport (icao, iata, airport_name, lat, lon, country_id) VALUES('LFBO', 'TLS', 'Toulouse–Blagnac Airport', 1.3678, 43.635, 5);
INSERT INTO airport (icao, iata, airport_name, lat, lon, country_id) VALUES('LSZH', 'ZRH', 'Zurich Airport', 5.5491, 47.4647, 6);
INSERT INTO airport (icao, iata, airport_name, lat, lon, country_id) VALUES('LKPR', 'PRG', 'Vaclav Havel Airport Prague', 14.26, 50.1008, 7);
INSERT INTO airport (icao, iata, airport_name, lat, lon, country_id) VALUES('LKMT', 'OSR', 'Leos Janacek Airport Ostrava', 18.1108, 49.6961, 7);
INSERT INTO airport (icao, iata, airport_name, lat, lon, country_id) VALUES('LKXB', 'KXB', 'Koberice International Airport', 18.038, 49.9571, 7);
INSERT INTO airport (icao, iata, airport_name, lat, lon, country_id) VALUES('WSSS', 'SIN', 'Singapore Changi Airport', 103.9894, 1.3592, 8);
INSERT INTO airport (icao, iata, airport_name, lat, lon, country_id) VALUES('OMDB', 'DXB', 'Dubai International Airport', 55.3644, 25.2528, 9);

INSERT INTO aircraft_type (designator, manufacturer, family, subtype) VALUES ('A35K', 'Airbus', 'A350', '-1000');
INSERT INTO aircraft_type (designator, manufacturer, family, subtype) VALUES ('A359', 'Airbus', 'A350', '-900');
INSERT INTO aircraft_type (designator, manufacturer, family, subtype) VALUES ('A388', 'Airbus', 'A380', '-800');
INSERT INTO aircraft_type (designator, manufacturer, family, subtype) VALUES ('A320', 'Airbus', 'A320', 'neo');
INSERT INTO aircraft_type (designator, manufacturer, family, subtype) VALUES ('A321', 'Airbus', 'A321', 'neo');
INSERT INTO aircraft_type (designator, manufacturer, family, subtype) VALUES ('BCS1', 'Airbus', 'A220', '-100');
INSERT INTO aircraft_type (designator, manufacturer, family, subtype) VALUES ('BCS3', 'Airbus', 'A220', '-300');
INSERT INTO aircraft_type (designator, manufacturer, family, subtype) VALUES ('B748', 'Boeing', '747', '-8i');
INSERT INTO aircraft_type (designator, manufacturer, family, subtype) VALUES ('B738', 'Boeing', '737', '-800');
INSERT INTO aircraft_type (designator, manufacturer, family, subtype) VALUES ('B77W', 'Boeing', '777', '-300ER');
INSERT INTO aircraft_type (designator, manufacturer, family, subtype) VALUES ('B787', 'Boeing', '787', '-800');

INSERT INTO airline (designator, airline_name) VALUES ('LH', 'Lufthansa');
INSERT INTO airline (designator, airline_name) VALUES ('LX', 'Swiss International Air Lines');
INSERT INTO airline (designator, airline_name) VALUES ('EK', 'Emirates');
INSERT INTO airline (designator, airline_name) VALUES ('DL', 'Delta Air Lines');
INSERT INTO airline (designator, airline_name) VALUES ('AA', 'American Airlines');
INSERT INTO airline (designator, airline_name) VALUES ('AF', 'Air France');
INSERT INTO airline (designator, airline_name) VALUES ('AC', 'Air Canada');
INSERT INTO airline (designator, airline_name) VALUES ('BA', 'British Airways');
INSERT INTO airline (designator, airline_name) VALUES ('OK', 'CSA');
INSERT INTO airline (designator, airline_name) VALUES ('QS', 'Smartwings');
INSERT INTO airline (designator, airline_name) VALUES ('SQ', 'Singapore Airlines');

INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
VALUES ();

