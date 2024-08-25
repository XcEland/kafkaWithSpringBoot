package com.example.smsConsumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.smsConsumer.model.SMS;
import com.example.smsConsumer.service.SMSService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/sms")
@Slf4j
public class SMSController {

    @Autowired
    SMSService smsService;

    @GetMapping("/test")
    public String testSMS(){
        return "todo";
    }

    @PostMapping("/process")
    public String processSMS(@RequestBody SMS sendRequest){
        log.info("processSMS started request: "+ sendRequest.toString());
        return smsService.sendSMS(sendRequest.getDestinationNumber(), sendRequest.getSmsMessage());
    }
}