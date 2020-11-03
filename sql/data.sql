INSERT INTO country (country_name, airport_prefix, registration_prefix) VALUES ('United States of America', 'K', 'N');
INSERT INTO country (country_name, airport_prefix, registration_prefix) VALUES ('Canada', 'C', 'C');
INSERT INTO country (country_name, airport_prefix, registration_prefix) VALUES ('Germany', 'ED', 'D');
INSERT INTO country (country_name, airport_prefix, registration_prefix) VALUES ('Great Britain', 'EG', 'G');
INSERT INTO country (country_name, airport_prefix, registration_prefix) VALUES ('France', 'LF', 'F');
INSERT INTO country (country_name, airport_prefix, registration_prefix) VALUES ('Switzerland', 'LS', 'HB');
INSERT INTO country (country_name, airport_prefix, registration_prefix) VALUES ('Czech Republic', 'LK', 'OK');
INSERT INTO country (country_name, airport_prefix, registration_prefix) VALUES ('Singapore', 'WS', '9V');
INSERT INTO country (country_name, airport_prefix, registration_prefix) VALUES ('United Arab Emirates', 'OM', 'A6');

INSERT INTO airport (icao, iata, airport_name, lat, lon, country_id) VALUES('----', '---', 'None', 17.903, 49.9392, 1);
INSERT INTO airport (icao, iata, airport_name, lat, lon, country_id) VALUES('KLAX', 'LAX', 'Los Angeles International Airport', -118.408, 33.9425, 1);
INSERT INTO airport (icao, iata, airport_name, lat, lon, country_id) VALUES('KJFK', 'JFK', 'John F. Kennedy International Airport', -73.7789, 40.64, 1);
INSERT INTO airport (icao, iata, airport_name, lat, lon, country_id) VALUES('KATL', 'ATL', 'Hartsfield–Jackson Atlanta International Airport', -84.4281, 33.6367, 1);
INSERT INTO airport (icao, iata, airport_name, lat, lon, country_id) VALUES('CYYZ', 'YYZ', 'Toronto Pearson International Airport', -79.6306, 43.6767, 2);
INSERT INTO airport (icao, iata, airport_name, lat, lon, country_id) VALUES('EDDF', 'FRA', 'Frankfurt Airport', 8.5706, 50.0333, 3);
INSERT INTO airport (icao, iata, airport_name, lat, lon, country_id) VALUES('EDDM', 'MUC', 'Munich Airport', 11.7861, 48.3539, 3);

INSERT INTO flight (number, departure, arrival, origin, destination, aircraft, current_flight) VALUES ('DUMMY', current_timestamp, current_timestamp, '----', '----', '------', 1);
