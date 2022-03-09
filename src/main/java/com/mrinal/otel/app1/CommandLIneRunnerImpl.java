package com.mrinal.otel.app1;

import com.mrinal.otel.app1.kafka.KakfaSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

public class CommandLIneRunnerImpl implements CommandLineRunner {

    private  final Logger  logger = LoggerFactory.getLogger(CommandLIneRunnerImpl.class);

    @Autowired
    KakfaSender kakfaSender;

    @Override
    public void run(String... args) throws Exception {
        logger.info("sending to Kafka through CLI");
        //kakfaSender.send1();
    }
}
