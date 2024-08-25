package com.example.emailConsumer.service;

import java.io.File;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.emailConsumer.config.MessagingConfig;
import com.example.emailConsumer.dto.Email;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("$(spring.mail.username)")
    private String fromEmailId;

    // @RabbitListener(queues = MessagingConfig.QUEUE)
    // public void recieveMessage(Email email){
        
    //     System.out.println("Received Email: "+ email.toString());
    //     String to = email.getTo();
    //     System.out.println("to: ");
    //     String subject = email.getSubject();
    //     String body = email.getBody();

    //     sendEmail(to, body, subject);
    //     System.out.println("Email Sent");

    // }

    public String sendEmail(String recipient, String body, String  subject){
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(fromEmailId);
            simpleMailMessage.setTo(recipient);
            simpleMailMessage.setText(body);
            simpleMailMessage.setSubject(subject);
            javaMailSender.send(simpleMailMessage);

            return "Success";
        } catch (Exception e) {
            return e.getMessage();
        }
        
    }

    @RabbitListener(queues = MessagingConfig.QUEUE)
    public void recieveEmailWithAttachment(Email email){
        
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

            helper.addAttachment("file.jpg", new File("C:\\Users\\Admin\\Desktop\\Integrations\\EmailSender\\src\\main\\resources\\static\\attachments\\file.jpg"));
            helper.addAttachment("doc.pdf", new File("C:\\Users\\Admin\\Desktop\\Integrations\\EmailSender\\src\\main\\resources\\static\\attachments\\doc.pdf"));
            javaMailSender.send(simpleMailMessage);
            return "Success!";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
