package com.company.micro_service_1.config;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author : LiWenBo
 * @program : research_project
 * @description :
 * @date : 2021-11-21 10:24:10
 */
@Data
@ConfigurationProperties(prefix = "redis")
@Configuration
public class RedisConfig {

    @NacosValue(value = "${host}", autoRefreshed = true)
    private String host;
    @NacosValue(value = "${port}", autoRefreshed = true)
    private Integer port;
    @NacosValue(value = "${password}", autoRefreshed = true)
    private String password;

}
