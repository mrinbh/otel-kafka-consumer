package com.mrinal.otel.app1.controller;

import com.mrinal.otel.app1.kafka.KafkaReceiver;
import com.mrinal.otel.app1.kafka.KakfaSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
@RequestMapping("/a2")
public class RestServiceController {

    Logger logger = LoggerFactory.getLogger(RestServiceController.class);

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/pingA2")
    public String ping(){
        logger.info("In Ping");
        return "Hello,This is ping from service A application 1";
    }

    @GetMapping("/callA1")
    public String call(){
        logger.info("In call");
        String response = restTemplate.getForObject("http://localhost:8080/v1/ping",String.class);
        logger.info("response is {}",response);
        return "returning from call B";
    }

    @GetMapping("/serviceA2")
    public String serviceA2(){
        logger.info("In application A2");
        return "returning from A2 serviceA2";
    }

}
