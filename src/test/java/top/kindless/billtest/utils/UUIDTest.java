package top.kindless.billtest.utils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import top.kindless.billtest.model.entity.*;
import top.kindless.billtest.repository.*;

import java.util.List;


@SpringBootTest
public class UUIDTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    DetailOrderRepository detailOrderRepository;

    @Autowired
    CommodityRepository commodityRepository;

    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    SpecificationRepository specificationRepository;

    @Autowired
    RedisCacheManager myCacheManager;

    @Autowired
    RedisTemplate<String, String> redisTemplate;
    @Test
    void testUUID(){
//        Optional<User> user = userRepository.findById("user_ed2a44b618ab425b8f3a6fdbd2d9e009");
//        System.out.println(user.get());
//        User user = userRepository.findByAccount("root");
//        System.out.println(user);
        User user = userRepository.findByAccountAndPassword("root", "123");
        if (user!=null){
            System.out.println("登陆成功！");
            return;
        }
        System.out.println("登陆失败");
    }

    @Test
    void testOrder(){
//        List<DetailOrder> detailOrders = detailOrderRepository.findAllByBillId("order_7949d94c49e3426691d96afb308196cc");
//        for (DetailOrder detailOrder : detailOrders) {
//            Inventory inventory = inventoryRepository.findById(detailOrder.getGoodsId()).get();
//            Commodity commodity = commodityRepository.findById(inventory.getCommodityId()).get();
//            Specification specification = specificationRepository.findById(inventory.getSpecificationId()).get();
//            System.out.println(commodity+"===="+specification);
//        }

        List<DetailOrder> all = detailOrderRepository.findAll(Sort.by("billId"));
        for (DetailOrder detailOrder : all) {
            System.out.println(detailOrder);
        }
    }

    @Test
    void testUUID1(){
        System.out.println(UUIDUtils.generateOrderUUID());
    }

    @Test
    void testRepos(){
        System.out.println(userRepository.findPasswordByAccount("string"));
    }

    @Test
    void testRedis(){
        String s = redisTemplate.opsForValue().get("user_57187dfad5694d858b7ed4d02be999da");
        System.out.println(s);
    }
}
