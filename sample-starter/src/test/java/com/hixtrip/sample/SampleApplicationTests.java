package com.hixtrip.sample;

import com.hixtrip.sample.infra.data.RedisUtils;
import com.hixtrip.sample.infra.data.RocketMqUtils;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.support.MessageBuilder;

@SpringBootTest
class SampleApplicationTests {

    @Autowired
    private RocketMqUtils rocketMqUtils;

    @Test
    void contextLoads() {
        String tradeNo = "6280946530414790";
        rocketMqUtils.asyncSend("PERSON_ADD", MessageBuilder.withPayload(tradeNo).build());
    }

    @Autowired
    private RedisUtils redisUtils;

    @Test
    public void saveValue() {
        //存入Redis
        redisUtils.set("username", "IT小辉同学");
        System.out.println("保存成功！！！");
        //根据key取出
        String username = (String) redisUtils.get("username");
        System.out.println("username="+username);
    }

}
