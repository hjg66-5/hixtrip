package com.hixtrip.sample;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = {"com.hixtrip"})
@MapperScan(basePackages = {"com.hixtrip.sample.infra.db.mapper"})
@Configurable
public class SampleApplication {

    public static void main(String[] args) {
        /*
         * 指定使用的日志框架，否则将会告警
         * RocketMQLog:WARN No appenders could be found for logger (io.netty.util.internal.InternalThreadLocalMap).
         * RocketMQLog:WARN Please initialize the logger system properly.
         */
        System.setProperty("rocketmq.client.logUseSlf4j", "true");
        SpringApplication.run(SampleApplication.class, args);
    }

}
