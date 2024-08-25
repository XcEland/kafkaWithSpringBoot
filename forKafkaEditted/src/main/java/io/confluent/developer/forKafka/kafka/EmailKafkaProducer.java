package io.confluent.developer.forKafka.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import io.confluent.developer.forKafka.model.Email;

@Service
public class EmailKafkaProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailKafkaProducer.class);

    private KafkaTemplate<String, Email> kafkaTemplate;

    public EmailKafkaProducer(KafkaTemplate<String, Email> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(Email data){

        LOGGER.info(String.format("Sending Message -> %s", data.toString()));

        Message<Email> message = MessageBuilder
        .withPayload(data)
        .setHeader(KafkaHeaders.TOPIC, "emails")
        .build();

        kafkaTemplate.send(message);
    }
}
