package com.example.smsConsumer.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.smsConsumer.config.MessagingConfig;
import com.example.smsConsumer.model.SMS;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SMSService {

    @Value("${TWILIO_ACCOUNT_SID}")
    String  ACCOUNT_SID;

    @Value("${TWILIO_AUTH_TOKEN}")
    String  AUTH_TOKEN;

    @Value("${TWILIO_OUTGOING_SMS_NUMBER}")
    String OUTGOING_SMS_NUMBER;

    public SMSService(){
        log.info("Creating class SMSService");
        log.info("ACCOUNT_SID: "+ ACCOUNT_SID);
    }

    @PostConstruct
    private void setup(){
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

    }

    @RabbitListener(queues = MessagingConfig.SMS_QUEUE)
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
