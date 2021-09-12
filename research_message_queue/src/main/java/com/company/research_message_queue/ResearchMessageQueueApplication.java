package com.company.research_message_queue;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class ResearchMessageQueueApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResearchMessageQueueApplication.class, args);
    }

}
