package com.fiona.mq.rocketmq.config;

import lombok.extern.log4j.Log4j2;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.context.annotation.Configuration;

@Configuration
@Log4j2
public class TestTransactionListener extends AbstractTransactionListener {

    @Override
    public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
                log.info("消息"+new String(msg.getBody()));

        return LocalTransactionState.UNKNOW;
    }

    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt msg) {
        System.out.println("未决事务，服务器回查客户端msg =" + new String(msg.getBody().toString()));

        //由于RocketMQ迟迟没有收到消息的确认消息，因此主动询问这条prepare消息，是否正常？
        //可以查询数据库看这条数据是否已经处理
        return LocalTransactionState.UNKNOW;
    }

}