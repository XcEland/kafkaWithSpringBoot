package io.confluent.developer.forKafka.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.confluent.developer.forKafka.kafka.JsonKafkaProducer;
import io.confluent.developer.forKafka.model.User;

@RestController
@RequestMapping("/api/v2/kafka")
public class JsonMessageController {
    
    private JsonKafkaProducer kafkaProducer;

    public JsonMessageController(JsonKafkaProducer kafkaProducer){
        this.kafkaProducer = kafkaProducer;
    }

    @PostMapping("/publish")
    public ResponseEntity<String> publish(@RequestBody User user){
        // User user = new User(61, "Jenny", "Duue");
        kafkaProducer.sendMessage(user);
        System.out.println("We are sending this user-> %s"+user.toString());
        return ResponseEntity.ok("Json message sent to kafka topic");

    }
}
