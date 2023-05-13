package com.yoo.basicBoot.common.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RedisUtil {

    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * Redis에 저장된 value 반환
     * @param key
     * @return String
     * */
    public Object getData(String key){
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(key);
    }

    /**
     * Redis에 저장
     * @param key, value */
    public void setDate(String key, Object value){
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key,value);
    }

    /**
     * Redis에 저장 (유효시간 지정)
     * @param key, value, duration */
    public void setDataExpire(String key , Object value, long duration){
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        Duration expireDuration = Duration.ofSeconds(duration);
        valueOperations.set(key,value, expireDuration);
    }

    /**
     * Redis key 삭제
     * @param key */
    public void deleteData(String key){
        redisTemplate.delete(key);
    }
}
