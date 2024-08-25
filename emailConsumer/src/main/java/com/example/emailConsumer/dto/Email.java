package com.example.emailConsumer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Email {
    private String emailId;
    private String subject;
    private String to;
    private String body;
}
