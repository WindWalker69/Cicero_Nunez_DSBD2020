package dsbd2020.project.productmanager.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.*;

import java.util.HashMap;
import java.util.Map;

@Configuration
class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${kafkaTopicOrders}")
    private String topicName;

    @Value("${kafkaGroup}")
    private String consumerGroup;

    // Bean di creazione di una mappa con i parametri di configurazione per il client kafka
    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapServers); // Host e porta sulla quale kafka è in ascolto
        props.put(ConsumerConfig.GROUP_ID_CONFIG,
                consumerGroup);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class); // Classe di serializzazione da utilizzare per deserializzare la chiave
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class); // Classe di serializzazione da utilizzare per deserializzare i valori
        return props;
    }

    @Bean
    public ConsumerFactory<String, String> producerFactoryConsumer() {
        // implementation to produce a new Consumer instance
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }
}

