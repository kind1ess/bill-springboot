package top.kindless.billtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import top.kindless.billtest.model.common.CheckListGoods;
import top.kindless.billtest.model.entity.DetailCheck;

import java.util.List;

public interface DetailCheckRepository extends JpaRepository<DetailCheck,Integer> {

    @Query(value = "select new top.kindless.billtest.model.common.CheckListGoods(d.id,d.goodsId,c.commodityName,s.specificationName,d.amount,d.refBillId,d.actualAmount,d.remark)" +
            "from DetailCheck d,Inventory i,Commodity c,Specification s " +
            "where d.goodsId = i.goodsId " +
            "and i.commodityId = c.id " +
            "and i.specificationId = s.id " +
            "and d.billId = :billId " +
            "order by d.goodsId,d.id")
    List<CheckListGoods> findListGoodsByBillId(@Param("billId") String billId);

    List<DetailCheck> findAllByBillId(String billId);
}
