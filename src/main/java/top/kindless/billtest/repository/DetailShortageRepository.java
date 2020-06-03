package top.kindless.billtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import top.kindless.billtest.model.common.CommonListGoods;
import top.kindless.billtest.model.entity.DetailShortage;

import java.util.List;

public interface DetailShortageRepository extends JpaRepository<DetailShortage,Integer> {

    @Query(value = "select new top.kindless.billtest.model.common.CommonListGoods(d.id,d.goodsId,c.commodityName,s.specificationName,d.amount)" +
            "from DetailShortage d,Inventory i,Commodity c,Specification s " +
            "where d.goodsId = i.goodsId " +
            "and i.commodityId = c.id " +
            "and i.specificationId = s.id " +
            "and d.billId = :billId " +
            "order by d.goodsId,d.id,d.billId")
    List<CommonListGoods> findListGoodsByBillId(@Param("billId") String billId);
}
