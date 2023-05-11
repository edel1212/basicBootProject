package com.yoo.basicBoot.common.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RedisUtil {

    private final RedisTemplate redisTemplate;

    /**
     * Redis에 저장된 value 반환
     * @param key
     * @return String
     * */
    public String getData(String key){
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(key);
    }

    /**
     * Redis에 저장
     * @param key, value
     * @return void
     * */
    public void setDate(String key, String value){
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key,value);
    }

    /**
     * Redis에 저장 (유효시간 지정)
     * @param key, value, duration
     * @return void
     * */
    public void setDataExpire(String key , String value, long duration){
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        Duration expireDuration = Duration.ofSeconds(duration);
        valueOperations.set(key,value, expireDuration);
    }

    /**
     * Redis key 삭제
     * @param key
     * @return void
     * */
    public void deleteData(String key){
        redisTemplate.delete(key);
    }
}
