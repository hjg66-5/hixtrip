package com.hixtrip.sample.entry;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RocketMQMessageListener(topic = "xfg-mq", consumerGroup = "xfg-group")
public class PersonMqListener implements RocketMQListener<String> {

    @Override
    public void onMessage(String s) {
        log.info("接收到MQ消息 {}", s);
    }
}

