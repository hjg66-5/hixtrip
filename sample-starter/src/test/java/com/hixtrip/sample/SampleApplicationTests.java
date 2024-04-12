package com.hixtrip.sample;

import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.infra.data.RedisUtils;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
class SampleApplicationTests {

    @Autowired
    private RedisUtils redisUtils;
    @Setter(onMethod_ = @Autowired)
    private RocketMQTemplate rocketmqTemplate;

    @Test
    public void saveValue() {
        //存入Redis
        redisUtils.set("username", "IT小辉同学");
        System.out.println("保存成功！！！");
        //根据key取出
        String username = (String) redisUtils.get("username");
        System.out.println("username="+username);
    }
    @Test
    public void saveValue1() {
        //存入Redis
        redisUtils.set("1", 100);
        System.out.println("保存成功！！！");
        //根据key取出
        String username = (String) redisUtils.get("1");
        System.out.println("1="+username);
    }


    @Test
    public void test() throws InterruptedException {
        while (true) {
            rocketmqTemplate.convertAndSend("xfg-mq", "我是测试消息1111");
            Thread.sleep(3000);
        }
    }
    @Autowired
    protected OrderRepository orderRepository;

    @Test
    void t1(){
        String skuId = orderRepository.querySkuIdByOrderId("6774262830360164");
    }


}
