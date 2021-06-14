package InterFlight.Kafka;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    private static final Logger logger = LogManager.getLogger(KafkaProducer.class);

    public void sendMessage(String topic, String message) {
        kafkaTemplate.send(topic, message);
    }

}