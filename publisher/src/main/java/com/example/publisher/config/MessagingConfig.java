package com.example.publisher.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagingConfig {

    public static final String QUEUE = "email_queue";
    public static final String EXCHANGE = "email_exchange";
    public static final String ROUTING_KEY = "email_routingKey";

    public static final String SMS_QUEUE = "sms_queue";
    public static final String SMS_EXCHANGE = "sms_exchange";
    public static final String SMS_ROUTING_KEY = "sms_routingKey";
  
    @Bean
    public Queue queue(){
        return new Queue(QUEUE);
    }

    @Bean
    public Queue smsQueue() {
        return new Queue(SMS_QUEUE);
    }

    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public TopicExchange smsExchange() {
        return new TopicExchange(SMS_EXCHANGE);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    @Bean
    public Binding smsBinding(Queue smsQueue, TopicExchange smsExchange) {
        return BindingBuilder.bind(smsQueue).to(smsExchange).with(SMS_ROUTING_KEY);
    }

    @Bean
    public MessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(RabbitTemplate rabbitTemplate){
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}
