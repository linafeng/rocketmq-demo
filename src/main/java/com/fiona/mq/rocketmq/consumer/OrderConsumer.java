package com.fiona.mq.rocketmq.consumer;

import com.fiona.mq.rocketmq.config.DefaultConsumerConfigure;
import com.fiona.mq.rocketmq.config.TestMsgOrderListener;
import lombok.extern.log4j.Log4j2;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Log4j2
@Configuration
public class OrderConsumer extends DefaultConsumerConfigure implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private TestMsgOrderListener testMsgOrderListener;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent arg0) {
        try {
            super.listener("order_TopicTest", "Tag1");
        } catch (MQClientException e) {
            log.error("消费者监听器启动失败", e);
        }
        
    }

    @Override
    public ConsumeConcurrentlyStatus dealBody(List<MessageExt> msgs)  {
        int num = 1;
        log.info("进入");
        for(MessageExt msg : msgs) {
            log.info("第" + num + "次消息");
            try {
                String msgStr = new String(msg.getBody(), "utf-8");
                log.info(msgStr);
            } catch (UnsupportedEncodingException e) {
                log.error("body转字符串解析失败");
            }
        }
        try {
            Thread.sleep(10000);
        }catch (Exception e){
            e.printStackTrace();
        }
        log.info("消费完");
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }

}