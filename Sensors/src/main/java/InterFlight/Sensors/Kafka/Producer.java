package InterFlight.Sensors.Kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import InterFlight.Sensors.Model.Flight;

//bin/kafka-topics.sh --create --topic interFlight --zookeeper localhost:2181 --partitions 1 --replication-factor 1

@Service
public class Producer {
    
    private static final String TOPIC = "interFlight";

    @Autowired
    private KafkaTemplate<String, Flight> kafkaTemplate;

    public void send(Flight message) {
        this.kafkaTemplate.send(TOPIC, message);
    }
}
