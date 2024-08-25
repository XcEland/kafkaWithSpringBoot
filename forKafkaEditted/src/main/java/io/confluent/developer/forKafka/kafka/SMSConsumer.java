package io.confluent.developer.forKafka.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import io.confluent.developer.forKafka.model.SMS;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SMSConsumer {

    @Value("${TWILIO_ACCOUNT_SID}")
    String  ACCOUNT_SID;

    @Value("${TWILIO_AUTH_TOKEN}")
    String  AUTH_TOKEN;

    @Value("${TWILIO_OUTGOING_SMS_NUMBER}")
    String OUTGOING_SMS_NUMBER;

    public SMSConsumer(){
        log.info("Creating class SMSService");
        log.info("ACCOUNT_SID: "+ ACCOUNT_SID);
    }

    @PostConstruct
    private void setup(){
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

    }

    @KafkaListener(topics = "sms", groupId = "myGroup")
    public void receiveSMS(SMS sms){

        System.out.println("Received sms: "+ sms.toString());
        String destination = sms.getDestinationNumber();
        String body = sms.getSmsMessage();

        sendSMS(destination, body);
        System.out.println("Sms Sent");
    }

    public String sendSMS(String smsNumber, String smsMessage){
        Message message = Message.creator(
            new PhoneNumber(smsNumber), 
            new PhoneNumber(OUTGOING_SMS_NUMBER), 
            smsMessage).create();

        return message.getStatus().toString();
    }
    
}
