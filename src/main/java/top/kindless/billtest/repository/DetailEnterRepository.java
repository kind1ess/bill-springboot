package top.kindless.billtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import top.kindless.billtest.model.common.ListGoodsWithoutPrice;
import top.kindless.billtest.model.common.FlowReserveAmount;
import top.kindless.billtest.model.entity.DetailEnter;

import java.util.List;

public interface DetailEnterRepository extends JpaRepository<DetailEnter,Integer> {

    /**
     * 根据入库单id查询入库单明细
     * @param billId 入库单id
     * @return 入库单明细
     */
    @Query(value = "select new top.kindless.billtest.model.common.ListGoodsWithoutPrice(d.id,d.goodsId,c.commodityName,s.specificationName,d.amount,d.checkId)" +
            "from DetailEnter d,Inventory i,Commodity c,Specification s " +
            "where d.goodsId = i.goodsId " +
            "and i.commodityId = c.id " +
            "and i.specificationId = s.id " +
            "and d.billId = :billId " +
            "order by d.goodsId,d.id")
    List<ListGoodsWithoutPrice> findListGoodsByBillId(@Param("billId") String billId);


    /**
     * 统计入库数量
     * @return
     */
    @Query(value = "select new top.kindless.billtest.model.common.FlowReserveAmount(d.goodsId,sum(d.amount))" +
            "from DetailEnter d group by d.goodsId")
    List<FlowReserveAmount> findAllSumAmount();
}
