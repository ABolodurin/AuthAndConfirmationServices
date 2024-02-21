package ru.bolodurin.confirmation.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.DelegatingByTopicDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Configuration
@EnableKafka
@RequiredArgsConstructor
public class KafkaConsumerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;
    @Value("${kafka.topics.sendConfirmationEmail}")
    private String topic;

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Object>> factory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    private Map<String, Object> config() {
        Map<String, Object> properties = new HashMap<>();

        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

//        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
//        properties.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
//        properties.put(JsonDeserializer.TRUSTED_PACKAGES, "*");


        return properties;
    }

    private ConsumerFactory<String, Object> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(config(), new StringDeserializer(), byTopicDeserializer());
    }

    private Deserializer<Object> byTopicDeserializer() {
        StringDeserializer deserializer = new StringDeserializer();
//        JsonDeserializer<EmailConfirmationMessage> deserializer = new JsonDeserializer<>();
//        deserializer.addTrustedPackages("*");
        return new DelegatingByTopicDeserializer(Map.of(
                Pattern.compile(topic), deserializer),
                new JsonDeserializer<>());
    }

}
