package com.hixtrip.sample;

import com.hixtrip.sample.infra.data.RedisUtils;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SampleApplicationTests {


    @Test
    void contextLoads() {
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
