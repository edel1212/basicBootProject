package com.yoo.basicBoot.config;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.time.Duration;

@SpringBootTest
@Log4j2
public class RedisTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void redisSetTest(){
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();

        valueOperations.set("keyTest","valueTest"); //만료가 없는 상태
        valueOperations.set("key-has-TimeLimit","value!", Duration.ofSeconds(30)); //30초
    }

    @Test
    public void redisGetTest(){
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String redisValue = valueOperations.get("keyTest");
        log.info("redisValue :::{}",redisValue);
    }

}
