package com.fiona.mq.rocketmq.config.consts;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@ConfigurationProperties(prefix = "rocketmq.consumer")
@EnableConfigurationProperties(ConsumerConfig.class)
@Configuration
@ToString
public class ConsumerConfig {
    private String groupName;
    
    private String namesrvAddr;
}