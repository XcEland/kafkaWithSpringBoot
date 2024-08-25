package io.confluent.developer.forKafka.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import io.confluent.developer.forKafka.model.User;

@Service
public class KafkaConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);
    @KafkaListener(topics = "dawns", groupId = "myGroup")
    public void consume(User user){
        LOGGER.info(String.format("Message received -> %s",user));
    }
}
