package com.example.publisher.controller;

import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.publisher.config.MessagingConfig;
import com.example.publisher.dto.SMS;

@RestController
@RequestMapping("/sms")
public class SMSPublisher {
    @Autowired
    private RabbitTemplate template; 
    
    @PostMapping("/{departmentName}")
    public String bookSMS(@RequestBody SMS sms, @PathVariable String departmentName){
        sms.setSmsId(UUID.randomUUID().toString());
        template.convertAndSend(MessagingConfig.SMS_EXCHANGE,MessagingConfig.SMS_ROUTING_KEY,sms);
        return  "SMS sent successfully!";
    }
}
