package ru.bolodurin.services.authentication.loging;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

class KafkaLogProducer {
    private final KafkaProducer<String, String> kafkaProducer;

    KafkaLogProducer(String bootstrapServers) {
        Properties properties = new Properties();

        properties.put("bootstrap.servers", bootstrapServers);
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        kafkaProducer = new KafkaProducer<>(properties);
    }

    void sendMessage(String topic, String key, String message) {
        kafkaProducer.send(new ProducerRecord<>(topic, key, message));
    }

}
