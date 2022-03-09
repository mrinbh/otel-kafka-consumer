package com.mrinal.otel.app1.kafka;

import com.mrinal.otel.app1.db.respositories.InvoiceRepository;
import com.mrinal.otel.app1.model.Invoice;
import io.opentelemetry.api.trace.*;
import io.opentelemetry.context.Context;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.SendTo;
import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.internal.ImmutableSpanContext;
//import io.opentelemetry.api.trace.PropagatedSpan;


import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class KafkaReceiver {

     @Value("${kakfa.topic.1}")
     String topic1;

     @Autowired
     KakfaSender kakfaSender;

     @Autowired
     InvoiceRepository invoiceRepository;



    Logger logger = LoggerFactory.getLogger(KafkaReceiver.class);

    //@KafkaListener(topics = "${kakfa.topic.1")
    @KafkaListener(topics = "mr-topic-1")
    //@SendTo("mr-topic-2")//Not Working
    public void receiveFromTopic1(String val, @Headers Map<String,Object> headers){
       logger.info("Inside  Kafka receiver for topic-1");
       headers.forEach((key,value) ->logger.info("key from topic-1 is: {} ,value is {}",key,
               value instanceof byte[] ? new String((byte[])value,StandardCharsets.UTF_8) : value));
       logger.info("received value {} from topic 1",val);
       logger.info("saving DB");
       invoiceRepository.save(new Invoice("CGS",256,"Invoice save"));
       logger.info("Done Saving");
       String kafkaVal= "some"+ UUID.randomUUID();
       kakfaSender.send(kafkaVal, "mr-topic-2");
       logger.info("Done Sending to kafka with value {}",kafkaVal);
    }


    @KafkaListener(topics = "mr-topic-2")
    public void receiveFromTopic2(String val, @Headers Map<String,Object> headers){
        logger.info("Inside  Kafka receiver for topic-2");
        headers.forEach((key,value) ->logger.info("key from topic-2 is: {} ,value is {}",key,value));
        logger.info("received value {} from topic 2 ",val);
    }

    private Span setTracer(String tracerParent){
      Tracer tracer = GlobalOpenTelemetry.getTracer("sample-app2");
      String traceId = tracerParent.substring(3,35);
        String spanId = tracerParent.substring(36,52);
       SpanContext spanContext = ImmutableSpanContext.create(traceId,spanId, TraceFlags.getDefault(), TraceState.getDefault(),true,false);
        //Context
        //Span span = io.opentelemetry.api.
        //Span.fromContext(spanContext);
        logger.info("Tracer id  {}",traceId);
        logger.info("spanId id  {}",spanId);
       Span span = tracer.spanBuilder("kafka-receiver")
              //.setParent()
              .startSpan();
       span.makeCurrent();
       logger.info("Tracer is {}",tracer);
       return span;
    }
}
