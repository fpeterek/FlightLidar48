package org.fpeterek.flightlidar48.generator

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import java.util.*

object KafkaWriter {

    private val topic = Config.topic

    private val properties: Properties
        get() {
            val props = Properties()
            props[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = Config.brokerList
            props[ProducerConfig.CLIENT_ID_CONFIG] = Config.producerId
            props[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer().javaClass.name
            props[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = StringSerializer().javaClass.name
            return props
        }

    private val producer = KafkaProducer<String, String>(properties)

    private fun createRecord(flight: Flight) = ProducerRecord<String, String>(topic, flight.toString())

    fun write(flight: Flight) {
        producer.send(createRecord(flight))
    }
}
