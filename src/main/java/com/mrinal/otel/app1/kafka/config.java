package com.mrinal.otel.app1.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class config {

    @Value("${kafka.boostrap.server}")
    private String bootStrapServer;

    @Bean
    public ProducerFactory<String,String> producerFactory() {


        ProducerFactory producerFactory = new DefaultKafkaProducerFactory(getKafkaConfig());
        return producerFactory;

    }
    @Bean
    public KafkaTemplate<String,String> kafkaTemplate() {
      return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public KakfaSender kakfaSender() {
        return new KakfaSender();
    }

    ///Receiver

    @Bean
    public ConsumerFactory<String,String> consumerFactory() {
        Map<String,Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,bootStrapServer);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.GROUP_ID_CONFIG,"mr-test");
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
        ConsumerFactory consumerFactory = new DefaultKafkaConsumerFactory(config);
        return consumerFactory;
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String,String>> kafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String,String> factory = new ConcurrentKafkaListenerContainerFactory();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @Bean
    public KafkaReceiver kafkaReceiver(){
       return new KafkaReceiver();
    }

    private Map<String,Object> getKafkaConfig(){
        Map<String,Object > config =new HashMap<String, Object>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,bootStrapServer);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return config;
    }


    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
