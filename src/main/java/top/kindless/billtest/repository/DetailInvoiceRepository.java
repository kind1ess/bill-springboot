package top.kindless.billtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import top.kindless.billtest.model.common.CommonListGoods;
import top.kindless.billtest.model.entity.DetailInvoice;

import java.util.List;

public interface DetailInvoiceRepository extends JpaRepository<DetailInvoice,Integer> {

    /**
     * 根据发货单id查询发货单明细集合
     * @param billId 发货单id
     * @return 发货单明细集合
     */
    List<DetailInvoice> findAllByBillId(String billId);

    /**
     * 根据发货单id查询订货单明细
     * @param billId 订货单id
     * @return 订货单明细
     */
    @Query(value = "select new top.kindless.billtest.model.common.CommonListGoods(d.id,d.goodsId,c.commodityName,s.specificationName,d.amount)" +
            "from DetailInvoice d,Inventory i,Commodity c,Specification s " +
            "where d.goodsId = i.goodsId " +
            "and i.commodityId = c.id " +
            "and i.specificationId = s.id " +
            "and d.billId = :billId " +
            "order by d.goodsId,d.id")
    List<CommonListGoods> findListGoodsByBillId(@Param("billId") String billId);
}
