package top.kindless.billtest.repository;

import org.omg.CORBA.INTERNAL;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import top.kindless.billtest.model.common.CommonBillPreviewWithUpdateTime;
import top.kindless.billtest.model.dto.PurchaseDto;
import top.kindless.billtest.model.entity.BillPurchase;

import java.util.List;

public interface BillPurchaseRepository extends JpaRepository<BillPurchase,String> {

    /**
     * 根据状态id查询
     * @param id
     * @return
     */
    List<BillPurchase> findAllByStatusId(Integer id);

    /**
     * 查询采购单所有id
     * @return
     */
    @Query(value = "select id from BillPurchase")
    List<String> findAllId();

    @Query(value = "select new top.kindless.billtest.model.dto.PurchaseDto(b.id,b.createTime,b.statusId,st.statusName,b.updateTime,b.staffId,s.name,s.telephone,b.sendTime,b.address)" +
            "from BillPurchase b,Staff s,Status st " +
            "where b.statusId = st.id " +
            "and b.staffId = s.id " +
            "and b.id = :id")
    PurchaseDto findPurchaseDtoById(@Param("id") String id);

    @Query(value = "select new top.kindless.billtest.model.common.CommonBillPreviewWithUpdateTime(b.id,b.createTime,b.updateTime,b.statusId,s.statusName)" +
            "from BillPurchase b,Status s " +
            "where b.statusId = s.id " +
            "order by b.updateTime desc ,b.statusId desc ,b.id")
    List<CommonBillPreviewWithUpdateTime> findAllPreview();

    @Query(value = "select new top.kindless.billtest.model.common.CommonBillPreviewWithUpdateTime(b.id,b.createTime,b.updateTime,b.statusId,s.statusName)" +
            "from BillPurchase b,Status s " +
            "where b.statusId = s.id " +
            "order by b.updateTime desc ,b.statusId desc ,b.id")
    List<CommonBillPreviewWithUpdateTime> findAllPreview(Pageable pageable);

    @Query(value = "select new top.kindless.billtest.model.common.CommonBillPreviewWithUpdateTime(b.id,b.createTime,b.updateTime,b.statusId,s.statusName)" +
            "from BillPurchase b,Status s " +
            "where b.statusId = s.id " +
            "and b.statusId = :statusId " +
            "order by b.updateTime desc ,b.id")
    List<CommonBillPreviewWithUpdateTime> findAllPreview(@Param("statusId") Integer statusId);

    @Query(value = "select new top.kindless.billtest.model.common.CommonBillPreviewWithUpdateTime(b.id,b.createTime,b.updateTime,b.statusId,s.statusName)" +
            "from BillPurchase b,Status s " +
            "where b.statusId = s.id " +
            "and b.statusId = :statusId " +
            "order by b.updateTime desc ,b.id")
    List<CommonBillPreviewWithUpdateTime> findAllPreview(@Param("statusId") Integer statusId,Pageable pageable);

    Long countAllByStatusId(Integer statusId);
}
