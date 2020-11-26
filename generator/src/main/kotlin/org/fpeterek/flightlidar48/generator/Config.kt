package org.fpeterek.flightlidar48.generator

import java.lang.RuntimeException

object Config {

    private fun getString(key: String, default: String): String {
        val value = System.getenv(key)

        return if (value.isNullOrBlank()) {
            default
        } else {
            value
        }
    }

    private fun getString(key: String): String {
        val value = System.getenv(key)

        if (value.isNullOrBlank()) {
            throw RuntimeException("Missing config for '$key'")
        }

        return value
    }

    private fun getInt(key: String, default: Int) = System.getenv(key)?.toIntOrNull() ?: default

    private fun getInt(key: String) =
        System.getenv(key)?.toIntOrNull() ?: throw RuntimeException("Missing or invalid config for '$key'")

    private val booleans = listOf("true", "1", "false", "0")

    private fun getBool(key: String, default: Boolean): Boolean {
        val value = getString(key, default.toString()).toLowerCase()

        return when {
            value.isBlank()    -> default
            value !in booleans -> throw RuntimeException("Invalid config for '$key'")
            else               -> value == "true" || value == "1"
        }
    }

    private fun getBool(key: String): Boolean {
        val value = getString(key).toLowerCase()

        return when (value) {
            !in booleans -> throw RuntimeException("Invalid config for '$key'")
            else -> value == "true" || value == "1"
        }
    }

    val locality = getString("LOCALITY", "dev")
    val topic = "${getString("TOPIC", "receiver-input-topic")}-$locality"
    val brokerList = getString("BROKER_LIST", "127.0.0.1:9092")
    val producerId = getString("PRODUCER_ID", "RandomDataGenerator")
    val receiverId = getInt("RECEIVER_ID", 1)
    val writeToStdout = getBool("WRITE_TO_STDOUT", false)
}
