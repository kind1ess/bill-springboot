package top.kindless.billtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import top.kindless.billtest.model.common.PurchaseListGoods;
import top.kindless.billtest.model.entity.DetailPurchase;

import java.util.List;

public interface DetailPurchaseRepository extends JpaRepository<DetailPurchase,Integer> {


    List<DetailPurchase> findAllByBillId(String billId);

    @Query(value = "select new top.kindless.billtest.model.common.PurchaseListGoods(d.id,d.goodsId,c.commodityName,s.specificationName,d.amount,c.price,d.shortageId)" +
            "from DetailPurchase d,Inventory i,Commodity c,Specification s " +
            "where d.goodsId = i.goodsId " +
            "and i.commodityId = c.id " +
            "and i.specificationId = s.id " +
            "order by d.goodsId,d.id,d.billId")
    List<PurchaseListGoods> findListGoodsByBillId(@Param("billId") String billId);
}
