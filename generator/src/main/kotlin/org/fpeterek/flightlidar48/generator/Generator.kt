package org.fpeterek.flightlidar48.generator

import kotlin.random.Random

object Generator {

    private const val latMin = 30.0
    private const val latMax = 60.0
    private const val lonMin = -30.0
    private const val lonMax = 50.0

    private val randLat
        get() = Random.nextDouble(latMin, latMax)

    private val randLon
        get() = Random.nextDouble(lonMin, lonMax)

    private val randSquawk
        get() = Random.nextInt(1000, 6000)

    private val randDirection
        get() = Random.nextInt(0, 3600) / 10.0

    private fun randAltitude(dir: Double) = when {
        dir < 270.0 && dir > 90.0 -> Random.nextInt(16, 20) * 2000
        else                      -> Random.nextInt(16, 20) * 2000 + 1000
    }

    private val randVelocity
        get() = Random.nextInt(410, 500)

    private val randOrigin
        get() = Data.airports.random()

    private fun randDest(exclude: String): String {
        val rand = Data.airports.random()

        return if (rand == exclude) {
            randDest(exclude)
        } else {
            rand
        }
    }

    private fun flightNumber(airline: String, lower: Int) = "$airline${Random.nextInt(lower, lower+10)}"

    private fun generateFlight(airline: String, aircraft: String, index: Int): Flight {
        val number = flightNumber(airline, index*10 + 1)
        val direction = randDirection
        val altitude = randAltitude(direction)
        val orig = randOrigin
        val dest = randDest(orig)

        return Flight(number, aircraft, randLat, randLon, randSquawk,
            altitude, direction, randVelocity, orig, dest)
    }

    private fun generateForAirline(airline: String, aircraft: List<String>) = aircraft.forEachIndexed { index, it ->
        flights.add(generateFlight(airline, it, index))
    }

    private val flights = mutableListOf<Flight>()

    private fun initialize() = Data.airlines.entries.forEach {
        generateForAirline(it.key, it.value)
    }

    private fun update(dt: Double) = flights.forEach {
        it.update(dt)
    }

    private fun output() = flights.forEach {
        if (Config.writeToStdout) {
            println(it)
        }
        KafkaWriter.write(it)
    }

    fun run() {
        initialize()

        var startTime = System.currentTimeMillis()

        while (true) {
            val current = System.currentTimeMillis()
            val deltaSeconds = (current - startTime) / 1000.0
            update(deltaSeconds)
            output()

            val deltaMillis = System.currentTimeMillis() - startTime
            Thread.sleep(if (deltaMillis >= 1000) { 0 } else { 1000 - deltaMillis })

            startTime = current
        }
    }

}

