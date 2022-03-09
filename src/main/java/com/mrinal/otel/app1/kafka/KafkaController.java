package com.mrinal.otel.app1.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/v1")
public class KafkaController {


    @Autowired
    KakfaSender kakfaSender;

    @GetMapping("/send")
    public String send(){
        //kakfaSender.send("Someval");
        return "sent to Kafa ";
    }
}
