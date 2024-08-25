package io.confluent.developer.forKafka.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.confluent.developer.forKafka.kafka.EmailKafkaProducer;
import io.confluent.developer.forKafka.kafka.SMSProducer;
import io.confluent.developer.forKafka.model.Email;
import io.confluent.developer.forKafka.model.SMS;


@RestController
@RequestMapping("/apis/")
public class JsonEmailController {
    private EmailKafkaProducer emailKafkaProducer;
    private SMSProducer smsProducer;

    public JsonEmailController(EmailKafkaProducer emailKafkaProducer, SMSProducer smsProducer){
        this.emailKafkaProducer = emailKafkaProducer;
        this.smsProducer = smsProducer;
    }

    @PostMapping("/email")
    public ResponseEntity<String> publishEmail(@RequestBody Email email){
        emailKafkaProducer.sendMessage(email);
        System.out.println("We are sending this email-> %s"+email.toString());
        return ResponseEntity.ok("Json email sent to kafka topic");

    }

    @PostMapping("/sms")
    public ResponseEntity<String> publishSMS(@RequestBody SMS sms){
        smsProducer.sendMessage(sms);
        System.out.println("We are sending this sms-> %s"+sms.toString());
        return ResponseEntity.ok("Json sms sent to kafka topic");

    }
}
