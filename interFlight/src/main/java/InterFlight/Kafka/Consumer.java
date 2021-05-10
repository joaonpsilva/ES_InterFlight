package InterFlight.Kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import InterFlight.Model.Flight;
import java.io.IOException;
import org.springframework.kafka.annotation.KafkaListener;

//bin/kafka-topics.sh --create --topic interFlight --zookeeper localhost:2181 --partitions 1 --replication-factor 1

@Service
public class Consumer {
    
    private static final String TOPIC = "interFlight";

    @Autowired
    private KafkaTemplate<String, Flight> kafkaTemplate;

    @KafkaListener(topics = TOPIC, groupId = "group_id")
    public void consume(String message) throws IOException {
        System.out.println("## -> Consumed message -> " +message);
    }
}
