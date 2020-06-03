package top.kindless.billtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import top.kindless.billtest.model.common.ListGoodsWithoutPrice;
import top.kindless.billtest.model.common.FlowReserveAmount;
import top.kindless.billtest.model.entity.DetailOdo;

import java.util.List;

public interface DetailOdoRepository extends JpaRepository<DetailOdo,Integer> {

    /**
     * 根据出库单编号查询所有明细
     * @param billId
     * @return
     */
    List<DetailOdo> findAllByBillId(String billId);
//
//    @Query(value = "select order_id from detail_odo where bill_id = ?1",nativeQuery = true)
//    List<String> findOrderIdByBillId(String billId);

    /**
     * 根据出库单id查询出库单明细
     * @param billId 出库单id
     * @return 出库单明细
     */
    @Query(value = "select new top.kindless.billtest.model.common.ListGoodsWithoutPrice(d.id,d.goodsId,c.commodityName,s.specificationName,d.amount,d.orderId)" +
            "from DetailOdo d,Inventory i,Commodity c,Specification s " +
            "where d.goodsId = i.goodsId " +
            "and i.commodityId = c.id " +
            "and i.specificationId = s.id " +
            "and d.billId = :billId " +
            "order by d.goodsId,d.id")
    List<ListGoodsWithoutPrice> findAllListGoodsByBillId(@Param("billId") String billId);


    @Query(value = "select new top.kindless.billtest.model.common.FlowReserveAmount(d.goodsId,sum(d.amount)) " +
            "from DetailOdo d group by d.goodsId")
    List<FlowReserveAmount> findAllSumAmount();
}
