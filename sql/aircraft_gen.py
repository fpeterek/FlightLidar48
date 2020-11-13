#!/usr/bin/python3

import random
from datetime import date


msn_cache = {}


type_age = {
    'A35K': 2,
    'A359': 3,
    'A388': 10,
    'A320': 4,
    'A321': 3,
    'BCS1': 4,
    'BCS3': 4,
    'B748': 8,
    'B738': 20,
    'B77W': 16,
    'B788': 9,
}


types = [t for t in type_age]


type_manufacturer = {
    'A35K': 'airbus',
    'A359': 'airbus',
    'A388': 'airbus',
    'A320': 'airbus',
    'A321': 'airbus',
    'BCS1': 'airbus',
    'BCS3': 'airbus',
    'B748': 'boeing',
    'B738': 'boeing',
    'B77W': 'boeing',
    'B788': 'boeing',
}


airline_fleets = {
    'LH': ['A359', 'A388', 'A320', 'A321', 'B748'],
    'LX': ['A320', 'BCS1', 'BCS3', 'B77W'],
    'EK': ['B77W', 'A388'],
    'DL': ['A35K', 'A320', 'A321', 'BCS3'],
    'AA': ['B77W', 'B788', 'B738', 'A320'],
    'AF': ['A359', 'A388', 'A35K', 'A320', 'A321', 'B77W'],
    'AC': ['B738', 'B788'],
    'BA': ['A35K', 'A388', 'A320', 'A321', 'B77W'],
    'OK': ['A320', 'BCS3', 'B738'],
    'QS': ['B738'],
    'SQ': ['A35K', 'A359', 'A388', 'B77W', 'B788']
}


airline_countries = {
    'LH': 'D',
    'LX': 'HB',
    'EK': 'A6',
    'DL': 'N',
    'AA': 'N',
    'AF': 'F',
    'AC': 'C',
    'BA': 'G',
    'OK': 'OK',
    'QS': 'OK',
    'SQ': '9V'
}


def get_msn(manufacturer: str) -> int:
    val = msn_cache.get(manufacturer, 0) + 1
    msn_cache[manufacturer] = val
    return val


def rand_letter() -> str:
    return random.choice('ABCDEFGHIJKLMNOPQRSTUVWXYZ')


generated_registrations = {}


def gen_registration(prefix: str) -> str:
    reg = f'{prefix}-{"".join([rand_letter() for _ in range(5-len(prefix))])}'
    return gen_registration(prefix) if reg in generated_registrations else reg


def rand_type(airline: str) -> str:
    return random.choice(airline_fleets[airline])


def get_first_flight(max_age: int) -> str:
    today = date.today()

    min_year = today.year - max_age
    year = random.choice(range(min_year, today.year))

    month = random.randint(1, 12 if year < today.year else today.month)

    # Dates don't really matter other than to ensure age calculation work properly 
    # so we can ignore the last couple days of the month to make the generator less complicated
    day_threshold = today.day if year == today.year and month == today.month else 28  
    day = random.randint(1, day_threshold)

    return date(year, month, day)


class Aircraft:
    def __init__(self, reg: str='', msn: int=0, airline: str='', type_des: str='', first_flight=None):
        self.reg = reg
        self.msn = msn
        self.airline = airline
        self.type_des = type_des
        self.first_flight = first_flight

    @staticmethod
    def random(airline: str):
        reg = gen_registration(airline_countries[airline])
        ac_type = rand_type(airline)
        msn = get_msn(type_manufacturer[ac_type])
        first_flight = get_first_flight(type_age[ac_type])
        return Aircraft(reg=reg, msn=msn, airline=airline, type_des=ac_type, first_flight=first_flight)

    @property
    def formatted_ff(self) -> str:
        return str(self.first_flight)  # self.first_flight.strftime('%Y-%m-%d')

    @property
    def insert(self) -> str:
        insert = 'INSERT INTO aircraft (registration, msn, airline, type_designator, first_flight)'
        values = f"VALUES('{self.reg}', {self.msn}, '{self.airline}', '{self.type_des}', '{self.first_flight}')"
        return insert + " \n    " + values + ';'


def generate(sql, kotlin):
    aircraft = []
    for airline in airline_countries:
        fleet = [Aircraft.random(airline) for _ in range(10)]
        aircraft += fleet

    kotlin.write('aircraft = listOf(\n')

    for index, ac in enumerate(aircraft):
        sql.write(ac.insert + '\n')
        kotlin.write(f'    "{ac.reg}"{"," if index != len(aircraft) - 1 else ""}\n')

    kotlin.write(')\n')



def run():
    with open('sql.sql', 'w') as sql:
        with open('kotlin.kt', 'w') as kotlin:
            generate(sql, kotlin)


if __name__ == '__main__':
    run()

