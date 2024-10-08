package io.confluent.developer.forKafka.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import io.confluent.developer.forKafka.model.User;

@Service
public class JsonKafkaProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonKafkaProducer.class);

    private KafkaTemplate<String, User> kafkaTemplate;

    public JsonKafkaProducer(KafkaTemplate<String, User> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(User data){

        LOGGER.info(String.format("Sending Message -> %s", data.toString()));

        Message<User> message = MessageBuilder
        .withPayload(data)
        .setHeader(KafkaHeaders.TOPIC, "dawns")
        .build();

        kafkaTemplate.send(message);
    }
}
