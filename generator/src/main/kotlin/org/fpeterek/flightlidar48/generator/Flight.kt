package org.fpeterek.flightlidar48.generator

import org.json.JSONObject
import kotlin.math.roundToInt
import kotlin.math.roundToLong
import kotlin.random.Random

data class Flight(
    val number: String,
    val aircraft: String,
    var lat: Double,
    var lon: Double,
    val squawk: Int,
    val altitude: Int,
    var direction: Double,
    val speed: Int,
    val origin: String,
    val destination: String,
) {

    private val Int.kmh
        get() = this * 1.852

    private enum class Bank(val value: Int) {
        Left(-1),
        Right(1),
        None(0);

        val notNone
            get() = this != None
    }

    private val bank = when (Random.nextInt(-1, 1)) {
        -1   -> Bank.Left
        1    -> Bank.Right
        else -> Bank.None
    }

    private fun bankAircraft(dt: Double) {
        direction += bank.value * dt
        direction %= 360.0
        direction += if (direction < 0) { 360.0 } else { 0.0 }
    }

    private fun adjustLat(lat: Double) = when {
        lat < -90.0 -> lat + 180.0
        lat > 90.0 -> lat - 180.0
        else -> lat
    }

    private fun adjustLon(lon: Double) = when {
        lon > 360.0 -> lon - 360.0
        lon < 0.0 -> lon + 360.0
        else -> lon
    }

    private fun move(dt: Double) {
        val kmh = speed.kmh
        val dx = kmh * dt
        val dy = kmh * dt

        val dlat = dy / 6_378_000 * 180 * Math.PI
        val newLat = lat + dlat

        val dlon = (dx / 6_378_000 * 180 * Math.PI) / Math.cos(Math.toRadians(newLat))
        val newLon = lon + dlon

        lat = adjustLat(newLat)
        lon = adjustLon(newLon)
    }

    fun update(dt: Double) {
        bankAircraft(dt)
        move(dt)
    }

    private val roundedDir
        get() = (direction * 100).roundToInt() / 100.0

    private fun roundCoordinate(coor: Double) = (coor * 10_000_000).roundToLong() / 10_000_000.0

    private val roundedLat
        get() = roundCoordinate(lat)

    private val roundedLon
        get() = roundCoordinate(lon)

    fun toJson() = JSONObject()
            .put("number", number)
            .put("aircraft", aircraft)
            .put("lat", roundedLat)
            .put("lon", roundedLon)
            .put("squawk", squawk)
            .put("altitude", altitude)
            .put("direction", roundedDir)
            .put("speed", speed)
            .put("origin", origin)
            .put("destination", destination)

    override fun toString() = toJson().toString()

}
