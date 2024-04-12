package com.hixtrip.sample.infra.data.listener;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(consumerGroup = "${rocketmq.producer.groupName}", topic = "PERSON_ADD")
public class PersonMqListener implements RocketMQListener<String> {

    @Override
    public void onMessage(String a) {
        System.out.println("接收到消息，开始消费..name:" + a);
    }
}

