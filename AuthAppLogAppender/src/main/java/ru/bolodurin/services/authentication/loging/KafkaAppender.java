package ru.bolodurin.services.authentication.loging;

import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Core;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Plugin(
        name = "KafkaAppender",
        category = Core.CATEGORY_NAME,
        elementType = Appender.ELEMENT_TYPE
)
public class KafkaAppender extends AbstractAppender {
    private final String topic;
    private final KafkaLogProducer kafkaProducer;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    protected KafkaAppender(String name, Filter filter, String bootstrapServers, String topic) {
        super(name, filter, null, false, null);
        kafkaProducer = new KafkaLogProducer(bootstrapServers);
        this.topic = topic;
    }

    @PluginFactory
    public static KafkaAppender createAppender(
            @PluginAttribute("name") String name,
            @PluginElement("Filter") Filter filter,
            @PluginAttribute("server") String bootstrapServers,
            @PluginAttribute("topic") String topic) {
        return new KafkaAppender(name, filter, bootstrapServers, topic);
    }

    public void append(final LogEvent event) {
        executorService.execute(() -> kafkaProducer.sendMessage(
                topic, event.getLoggerName(), event.getMessage().getFormattedMessage()));
    }

}
