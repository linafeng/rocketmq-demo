package com.fiona.mq.rocketmq.web;

import com.alibaba.fastjson.JSON;
import com.fiona.mq.rocketmq.config.TestMsgOrderListener;
import com.fiona.mq.rocketmq.config.TestTransactionListener;
import lombok.extern.log4j.Log4j2;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/test")
@Log4j2
public class TestController {

    //@Autowired
  //  private DefaultMQProducer defaultMQProducer;

    @Autowired
    private TransactionMQProducer producer;
    @Autowired
    private TestTransactionListener testTransactionListener;



    @GetMapping("/test")
    public void test(String info) throws Exception {
        Message message = new Message("t_TopicTest", "Tag1", "12345", "rocketmq测试成功".getBytes());
        // 这里用到了这个mq的异步处理，类似ajax，可以得到发送到mq的情况，并做相应的处理
        //不过要注意的是这个是异步的
        System.out.println(Objects.isNull(producer.getTransactionCheckListener()));
        producer.send(message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("传输成功");
                log.info(JSON.toJSONString(sendResult));
            }

            @Override
            public void onException(Throwable e) {
                log.error("传输失败", e);
            }
        });
    }

    @GetMapping("t_test")
    public void Ttest(String info) throws Exception {
        Message message = new Message("t_TopicTest", "Tag1", "12345", ("rocketmq msg "+info).getBytes());
        System.out.println(Objects.isNull(producer.getTransactionCheckListener()));
        producer.setTransactionListener(testTransactionListener);
        producer.setSendMsgTimeout(5000);
        producer.sendMessageInTransaction(message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("传输成功");
                
                log.info(JSON.toJSONString(sendResult));
            }
            @Override
            public void onException(Throwable e) {
                log.error("传输失败", e);
            }
        });
    }
    @GetMapping("order")
    public void ordertest(String info) throws Exception {
        producer.setTransactionListener();
        producer.setSendMsgTimeout(5000);

        for(int i=0;i<5;i++){
            Message message = new Message("order_TopicTest", "Tag1", "12345", ("rocketmq msg "+info).getBytes());
            System.out.println(Objects.isNull(producer.getTransactionCheckListener()));
            producer.send(message);
        }

    }

}