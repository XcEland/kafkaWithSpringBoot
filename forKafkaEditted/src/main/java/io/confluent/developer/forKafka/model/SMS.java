package io.confluent.developer.forKafka.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class  SMS {
    private String smsId;
    private String destinationNumber;
    private String smsMessage;
}