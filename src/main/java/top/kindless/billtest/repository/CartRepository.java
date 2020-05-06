package top.kindless.billtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import top.kindless.billtest.model.entity.Cart;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart,Integer> {

    List<Cart> findAllByUserId(String userId);

    Cart findByUserIdAndGoodsId(String userId,Integer goodsId);

    @Query(value = "select user_id from tb_cart where id=?1",nativeQuery = true)
    String findUserIdById(Integer id);
}
