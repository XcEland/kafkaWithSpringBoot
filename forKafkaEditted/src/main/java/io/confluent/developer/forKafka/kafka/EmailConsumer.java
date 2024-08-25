package io.confluent.developer.forKafka.kafka;

import java.io.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import io.confluent.developer.forKafka.model.Email;
import jakarta.mail.internet.MimeMessage;


@Service
public class EmailConsumer {
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("$(spring.mail.username)")
    private String fromEmailId;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailConsumer.class);
    
    @KafkaListener(topics = "emails", groupId = "myGroup")
    public void consume(Email email){
        LOGGER.info(String.format("Message received by email consumer -> %s",email));
        String to = email.getTo();
        String subject = email.getSubject();
        String body = email.getBody();
        sendEmailWithAttachment(to, body, subject);
        System.out.println("Email Sent");
    }

    public String sendEmailWithAttachment(String recipient, String body, String  subject){
        try {
            MimeMessage simpleMailMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(simpleMailMessage, true);
            helper.setFrom(fromEmailId);
            helper.setTo(recipient);
            helper.setText(body);
            helper.setSubject(subject);

            helper.addAttachment("another.png", new File("C:\\Users\\Admin\\Desktop\\kafkaWithSpringBoot\\forKafka\\src\\main\\resources\\templates\\emails\\another.png"));
            helper.addAttachment("image.png", new File("C:\\Users\\Admin\\Desktop\\kafkaWithSpringBoot\\forKafka\\src\\main\\resources\\templates\\emails\\image.png"));

            javaMailSender.send(simpleMailMessage);
            return "Success!";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
