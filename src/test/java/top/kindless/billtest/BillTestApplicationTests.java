package top.kindless.billtest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import top.kindless.billtest.model.common.CommonGoodsInfo;
import top.kindless.billtest.model.dto.CartDto;
import top.kindless.billtest.model.entity.BillOrder;
import top.kindless.billtest.repository.BillOrderRepository;
import top.kindless.billtest.repository.CartRepository;
import top.kindless.billtest.utils.UUIDUtils;

@SpringBootTest
class BillTestApplicationTests {

//    @Autowired
//    CartRepository cartRepository;
//
//    @Autowired
//    BillOrderRepository billOrderRepository;
//    @Autowired
//    RedisTemplate<String,Object> redisTemplate;
    @Test
    void contextLoads() {
//        String userId = cartRepository.findUserIdById(1);
//        System.out.println(userId);
//        Object o = redisTemplate.opsForValue().get("3f219fc3516b49a79922e465c9793951");
//        assert o != null;
//        System.out.println(o.getClass());
    }

    @Test
    void testOrderService(){
//        BillOrder billOrder = new BillOrder();
//        billOrder.setId(UUIDUtils.generateOrderUUID());
//        billOrder.setStatusId(1);
//        billOrder.setUserId(UUIDUtils.generateUserUUID());
//        billOrderRepository.saveAndFlush(billOrder);
//        System.out.println(billOrder);
    }

}
