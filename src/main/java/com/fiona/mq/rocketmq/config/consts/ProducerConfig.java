package com.fiona.mq.rocketmq.config.consts;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Getter
@Setter
@ConfigurationProperties(prefix = "rocketmq.producer")
@EnableConfigurationProperties(ProducerConfig.class)
@Configuration
@ToString
@Primary
public class ProducerConfig {
    private String namesrvAddr;
    
    private String groupName;
}