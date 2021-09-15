package com.company.research_message_queue.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author : LiWenBo
 * @program : research_project
 * @description :
 * @date : 2021-09-12 13:31:02
 */
@ConfigurationProperties(prefix = "spring.rabbitmq")
@Component
@Data
public class MyRabbitProperties {
    private String host;
    private Integer port;
    private String username;
    private String password;
}
