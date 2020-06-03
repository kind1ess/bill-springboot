package top.kindless.billtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import top.kindless.billtest.model.dto.CartDto;
import top.kindless.billtest.model.entity.Cart;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart,Integer> {

    /**
     * 根据用户id查询所有购物车实体
     * @param userId 用户id
     * @return 购物车实体
     */
    List<Cart> findAllByUserId(String userId);

    /**
     * 根据用户id和货物id查询
     * @param userId 用户id
     * @param goodsId 货物id
     * @return 购物车
     */
    Cart findByUserIdAndGoodsId(String userId,Integer goodsId);

    /**
     * 根据购物车查询用户id
     * @param id 购物车id
     * @return 用户id
     */
    @Query(value = "select user_id from tb_cart where id=?1",nativeQuery = true)
    String findUserIdById(Integer id);

    /**
     * 查询用户的所有购物车信息
     * @param userId 用户id
     * @return 所有购物车信息
     */
    @Query(value = "select new top.kindless.billtest.model.dto.CartDto(c.goodsId,co.id,co.commodityName,s.id,s.specificationName,co.price,co.imgUrl,c.id,c.amount)" +
            "from Cart c,Inventory i,Commodity co,Specification s " +
            "where c.goodsId = i.goodsId " +
            "and i.commodityId = co.id " +
            "and i.specificationId = s.id " +
            "and c.userId = :userId " +
            "order by c.id")
    List<CartDto> findAllCartDtoByUserId(@Param("userId") String userId);

    @Query(value = "select new top.kindless.billtest.model.dto.CartDto(c.goodsId,co.id,co.commodityName,s.id,s.specificationName,co.price,co.imgUrl,c.id,c.amount)" +
            "from Cart c,Inventory i,Commodity co,Specification s " +
            "where c.goodsId = i.goodsId " +
            "and i.commodityId = co.id " +
            "and i.specificationId = s.id " +
            "and c.id = :id")
    CartDto findCartDtoById(@Param("id") Integer id);
}
