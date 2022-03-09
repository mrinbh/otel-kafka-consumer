package com.mrinal.otel.app1.kafka;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class KakfaSender {

   Logger logger = LoggerFactory.getLogger(KakfaSender.class);

    @Autowired
    KafkaTemplate kafkaTemplate;


    public void send(String value,String topicName){
        logger.info("Sending to kafka ");
        String start = ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_INSTANT);
        ProducerRecord record= new ProducerRecord(topicName,value);
        record.headers().add("someHead","someHeadVal".getBytes());
        // kafkaTemplate.send(addTraceHeaders(record));
        kafkaTemplate.send(record);
        logger.info("Sent Record to topic {} with value :{}",topicName);
    }
}
