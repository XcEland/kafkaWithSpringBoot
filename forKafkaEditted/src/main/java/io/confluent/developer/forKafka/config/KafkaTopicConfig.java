package io.confluent.developer.forKafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    
    @Bean
    public NewTopic dawnsTopic(){
        return TopicBuilder.name("dawns")
        .build();
    }

    @Bean
    public NewTopic emailsTopic(){
        return TopicBuilder.name("emails")
        .build();
    }

    @Bean
    public NewTopic smsTopic(){
        return TopicBuilder.name("sms")
        .build();
    }
}
