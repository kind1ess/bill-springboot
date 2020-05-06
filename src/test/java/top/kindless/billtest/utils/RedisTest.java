package top.kindless.billtest.utils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTest {

    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    @Test
    void testRedis(){
        redisTemplate.delete("111");
    }
}
