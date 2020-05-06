package top.kindless.billtest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import top.kindless.billtest.repository.CartRepository;

@SpringBootTest
class BillTestApplicationTests {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    RedisTemplate<String,Object> redisTemplate;
    @Test
    void contextLoads() {
//        String userId = cartRepository.findUserIdById(1);
//        System.out.println(userId);
        Object o = redisTemplate.opsForValue().get("3f219fc3516b49a79922e465c9793951");
        assert o != null;
        System.out.println(o.getClass());
    }

}
