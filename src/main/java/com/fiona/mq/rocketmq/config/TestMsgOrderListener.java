package com.fiona.mq.rocketmq.config;

import lombok.extern.log4j.Log4j2;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
@Log4j2
public class TestMsgOrderListener implements MessageListenerOrderly {
    @Override
    public ConsumeOrderlyStatus consumeMessage(List<MessageExt> var1, ConsumeOrderlyContext var2) {
        // 设置自动提交
        var2.setAutoCommit(true);
        for (MessageExt msg : var1) {
            System.out.println(msg + ",内容：" + new String(msg.getBody()));
        }

        try {
            TimeUnit.SECONDS.sleep(5L);
        } catch (InterruptedException e) {

            e.printStackTrace();
        }
        ;

        return ConsumeOrderlyStatus.SUCCESS;
    }

}