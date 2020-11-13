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
    VALUES('D-HXRO', 1, 'LH', 'A388', '2010-03-23');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('D-QPWJ', 2, 'LH', 'A359', '2017-07-05');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('D-LHTH', 3, 'LH', 'A359', '2018-04-22');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('D-ECBD', 4, 'LH', 'A320', '2016-07-02');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('D-HJZJ', 1, 'LH', 'B748', '2014-01-05');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('D-BYHE', 5, 'LH', 'A320', '2016-04-18');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('D-QUPS', 6, 'LH', 'A321', '2018-06-13');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('D-GHZT', 2, 'LH', 'B748', '2018-11-13');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('D-ZUQX', 7, 'LH', 'A359', '2018-09-11');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('D-YVFR', 8, 'LH', 'A321', '2018-10-11');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('HB-YXK', 9, 'LX', 'A320', '2019-09-12');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('HB-ASF', 10, 'LX', 'BCS1', '2016-07-23');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('HB-OTF', 11, 'LX', 'A320', '2016-08-12');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('HB-ZNE', 12, 'LX', 'BCS3', '2018-08-05');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('HB-DVV', 13, 'LX', 'BCS1', '2018-12-25');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('HB-IWO', 3, 'LX', 'B77W', '2007-09-05');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('HB-RAJ', 4, 'LX', 'B77W', '2011-04-22');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('HB-HRS', 14, 'LX', 'BCS1', '2019-02-09');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('HB-HZF', 5, 'LX', 'B77W', '2015-07-04');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('HB-CXB', 15, 'LX', 'BCS1', '2017-07-23');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('A6-VYM', 16, 'EK', 'A388', '2018-09-15');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('A6-GWF', 17, 'EK', 'A388', '2016-05-11');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('A6-YNI', 18, 'EK', 'A388', '2014-08-02');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('A6-BBA', 6, 'EK', 'B77W', '2011-12-10');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('A6-FUG', 7, 'EK', 'B77W', '2009-01-15');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('A6-ETT', 19, 'EK', 'A388', '2010-01-05');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('A6-HES', 8, 'EK', 'B77W', '2008-06-06');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('A6-PPK', 20, 'EK', 'A388', '2015-04-10');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('A6-LKX', 21, 'EK', 'A388', '2011-04-03');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('A6-YPO', 22, 'EK', 'A388', '2015-04-13');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('N-XUCA', 23, 'DL', 'BCS3', '2017-06-01');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('N-QTUW', 24, 'DL', 'A35K', '2018-09-11');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('N-DMPQ', 25, 'DL', 'A320', '2019-08-05');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('N-HNGX', 26, 'DL', 'A35K', '2019-06-27');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('N-LCCI', 27, 'DL', 'A320', '2016-08-02');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('N-WFXT', 28, 'DL', 'BCS3', '2018-06-10');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('N-NLLR', 29, 'DL', 'BCS3', '2016-02-22');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('N-SHGX', 30, 'DL', 'BCS3', '2019-04-21');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('N-XSUR', 31, 'DL', 'A35K', '2018-11-26');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('N-GOIM', 32, 'DL', 'A320', '2017-03-12');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('N-QHFO', 33, 'AA', 'A320', '2016-09-11');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('N-IUUF', 9, 'AA', 'B738', '2013-06-02');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('N-SNMO', 10, 'AA', 'B77W', '2006-10-19');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('N-TLNI', 11, 'AA', 'B788', '2017-08-05');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('N-CIFD', 34, 'AA', 'A320', '2018-08-17');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('N-DGOK', 12, 'AA', 'B788', '2016-04-04');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('N-TEOO', 13, 'AA', 'B77W', '2011-12-01');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('N-FNXK', 14, 'AA', 'B77W', '2018-05-12');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('N-ZANN', 35, 'AA', 'A320', '2019-09-12');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('N-GPUX', 36, 'AA', 'A320', '2017-11-19');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('F-NYRH', 37, 'AF', 'A388', '2017-09-28');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('F-VCTC', 38, 'AF', 'A388', '2014-04-03');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('F-LBXF', 39, 'AF', 'A35K', '2019-04-03');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('F-YWKV', 40, 'AF', 'A388', '2015-04-18');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('F-VRMN', 15, 'AF', 'B77W', '2014-10-26');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('F-DKYF', 41, 'AF', 'A35K', '2019-08-07');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('F-QJNZ', 16, 'AF', 'B77W', '2014-12-11');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('F-ROLV', 42, 'AF', 'A388', '2016-11-08');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('F-CSFX', 17, 'AF', 'B77W', '2015-04-25');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('F-DRRY', 43, 'AF', 'A359', '2018-04-08');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('C-YTSD', 18, 'AC', 'B738', '2000-08-27');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('C-SKYK', 19, 'AC', 'B738', '2001-04-17');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('C-IUSH', 20, 'AC', 'B788', '2014-01-13');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('C-BHDP', 21, 'AC', 'B788', '2015-04-01');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('C-UNFG', 22, 'AC', 'B738', '2011-10-15');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('C-YTBW', 23, 'AC', 'B788', '2015-05-05');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('C-WSXE', 24, 'AC', 'B738', '2012-12-23');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('C-ZZQO', 25, 'AC', 'B738', '2012-02-26');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('C-JNLE', 26, 'AC', 'B788', '2011-01-08');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('C-HLOW', 27, 'AC', 'B738', '2000-10-23');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('G-JVPJ', 44, 'BA', 'A320', '2017-02-22');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('G-HHOH', 45, 'BA', 'A388', '2012-05-26');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('G-MYXS', 46, 'BA', 'A388', '2010-07-04');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('G-MWWN', 47, 'BA', 'A320', '2017-06-18');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('G-DWCF', 48, 'BA', 'A321', '2019-12-05');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('G-ZERJ', 49, 'BA', 'A35K', '2018-07-21');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('G-ICZB', 50, 'BA', 'A320', '2018-01-16');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('G-IFEO', 51, 'BA', 'A321', '2019-02-03');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('G-KYXZ', 52, 'BA', 'A321', '2017-09-07');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('G-ZZDX', 28, 'BA', 'B77W', '2013-03-03');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('OK-YJI', 53, 'OK', 'BCS3', '2018-03-15');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('OK-MEH', 54, 'OK', 'A320', '2017-09-16');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('OK-RTE', 55, 'OK', 'BCS3', '2017-09-02');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('OK-PSH', 29, 'OK', 'B738', '2009-04-12');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('OK-OBV', 56, 'OK', 'BCS3', '2016-01-24');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('OK-XER', 57, 'OK', 'A320', '2017-09-17');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('OK-HHM', 58, 'OK', 'BCS3', '2019-08-20');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('OK-WOV', 59, 'OK', 'BCS3', '2017-12-22');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('OK-HSL', 30, 'OK', 'B738', '2001-09-18');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('OK-TUV', 60, 'OK', 'A320', '2016-10-16');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('OK-TXN', 31, 'QS', 'B738', '2008-03-20');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('OK-HZZ', 32, 'QS', 'B738', '2001-10-14');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('OK-QCP', 33, 'QS', 'B738', '2019-03-14');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('OK-QVG', 34, 'QS', 'B738', '2007-05-08');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('OK-RMT', 35, 'QS', 'B738', '2008-12-10');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('OK-KAB', 36, 'QS', 'B738', '2013-04-28');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('OK-RQW', 37, 'QS', 'B738', '2007-06-01');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('OK-HHQ', 38, 'QS', 'B738', '2004-02-01');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('OK-MUS', 39, 'QS', 'B738', '2016-04-21');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('OK-SUK', 40, 'QS', 'B738', '2006-09-03');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('9V-JOZ', 41, 'SQ', 'B77W', '2007-12-20');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('9V-DTE', 61, 'SQ', 'A35K', '2018-02-24');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('9V-OOK', 62, 'SQ', 'A35K', '2019-07-24');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('9V-URH', 42, 'SQ', 'B788', '2017-05-05');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('9V-SKH', 63, 'SQ', 'A388', '2011-04-18');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('9V-XLH', 64, 'SQ', 'A35K', '2018-04-25');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('9V-CWU', 43, 'SQ', 'B788', '2019-07-06');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('9V-ELE', 65, 'SQ', 'A359', '2017-03-23');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('9V-HTP', 66, 'SQ', 'A35K', '2018-08-10');
INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)
    VALUES('9V-FSH', 67, 'SQ', 'A359', '2018-06-05');


