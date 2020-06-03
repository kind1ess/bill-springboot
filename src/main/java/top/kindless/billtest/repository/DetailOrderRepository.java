package top.kindless.billtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import top.kindless.billtest.model.common.ListGoods;
import top.kindless.billtest.model.entity.DetailOrder;

import java.util.List;

public interface DetailOrderRepository extends JpaRepository<DetailOrder,Integer> {

    /**
     * 根据订单编号查询所有订单详情
     * @param billId
     * @return
     */
    List<DetailOrder> findAllByBillId(String billId);

    /**
     * 根据订单编号删除所有订单详情
     * @param billId
     */
    @Modifying
    void deleteAllByBillId(String billId);

    /**
     * 根据订单编号查询订单详情
     * @param billId 订单编号
     * @return 订单详情
     */
    @Query(value = "select new top.kindless.billtest.model.common.ListGoods(d.id,d.goodsId,c.commodityName,s.specificationName,d.amount,c.price)" +
            "from DetailOrder d,Inventory i,Commodity c,Specification s  " +
            "where d.goodsId = i.goodsId " +
            "and i.commodityId = c.id " +
            "and i.specificationId = s.id " +
            "and d.billId = :billId " +
            "order by d.goodsId,d.id")
    List<ListGoods> findAllListGoodsByBillId(@Param("billId") String billId);
}
