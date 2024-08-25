package io.confluent.developer.forKafka.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import io.confluent.developer.forKafka.model.SMS;

@Service
public class SMSProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailKafkaProducer.class);

    private KafkaTemplate<String, SMS> kafkaTemplate;

    public SMSProducer(KafkaTemplate<String, SMS> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(SMS data){

        LOGGER.info(String.format("Sending Message -> %s", data.toString()));

        Message<SMS> message = MessageBuilder
        .withPayload(data)
        .setHeader(KafkaHeaders.TOPIC, "sms")
        .build();

        kafkaTemplate.send(message);
    }
}
