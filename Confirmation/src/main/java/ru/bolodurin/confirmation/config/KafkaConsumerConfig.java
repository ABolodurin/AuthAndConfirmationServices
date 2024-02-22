package ru.bolodurin.confirmation.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.DelegatingByTopicDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import ru.bolodurin.services.authentication.kafka.message.EmailConfirmationMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Configuration
@RequiredArgsConstructor
public class KafkaConsumerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;
    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;
    @Value("${kafka.topics.sendConfirmationEmail}")
    private String confirmTopic;
    @Value("${kafka.topics.log}")
    private String logTopic;

    @Bean
    public KafkaListenerContainerFactory<
            ConcurrentMessageListenerContainer<String, Object>> kafkaContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @Bean
    public Map<String, Object> config() {
        Map<String, Object> properties = new HashMap<>();

        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

        return properties;
    }

    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(config(), new StringDeserializer(), byTopicDeserializer());
    }

    @Bean
    public Deserializer<Object> byTopicDeserializer() {
        JsonDeserializer<EmailConfirmationMessage> deserializer = new JsonDeserializer<>();
        StringDeserializer byDefault = new StringDeserializer();
        return new DelegatingByTopicDeserializer(Map.of(
                Pattern.compile(confirmTopic), deserializer,
                Pattern.compile(logTopic), byDefault),
                byDefault);
    }

}
