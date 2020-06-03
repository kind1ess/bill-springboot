package top.kindless.billtest.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import top.kindless.billtest.model.common.CommonBillPreviewWithUpdateTime;
import top.kindless.billtest.model.dto.InvoiceDto;
import top.kindless.billtest.model.entity.BillInvoice;

import java.util.List;

public interface BillInvoiceRepository extends JpaRepository<BillInvoice,String> {

    /**
     * 根据发货单id查询发货单你表头
     * @param billId 发货单id
     * @return 发货单表头
     */
    @Query(value = "select new top.kindless.billtest.model.dto.InvoiceDto(b.id,b.createTime,b.statusId,s.statusName,b.updateTime,b.staffId,st.name,st.telephone,b.odoId,b.orderId,u.address,u.telephone)" +
            "from BillInvoice b,Status s,Staff st,BillOrder bo,User u " +
            "where b.statusId = s.id " +
            "and b.staffId = st.id " +
            "and b.orderId = bo.id " +
            "and bo.userId = u.id " +
            "and b.id = :billId")
    InvoiceDto findInvoiceDtoByBillId(@Param("billId") String billId);

    /**
     * 查询所有发货单预览信息
     * @return 所有发货单预览信息
     */
    @Query(value = "select new top.kindless.billtest.model.common.CommonBillPreviewWithUpdateTime(b.id,b.createTime,b.updateTime,b.statusId,s.statusName)" +
            "from BillInvoice b,Status s " +
            "where b.statusId = s.id " +
            "order by b.updateTime desc ,b.statusId desc ,b.id")
    List<CommonBillPreviewWithUpdateTime> findAllInvoicePreview();

    /**
     * 查询所有发货单预览信息
     * @return 所有发货单预览信息
     */
    @Query(value = "select new top.kindless.billtest.model.common.CommonBillPreviewWithUpdateTime(b.id,b.createTime,b.updateTime,b.statusId,s.statusName)" +
            "from BillInvoice b,Status s " +
            "where b.statusId = s.id " +
            "order by b.updateTime desc ,b.statusId desc ,b.id")
    List<CommonBillPreviewWithUpdateTime> findAllInvoicePreview(@NonNull Pageable pageable);

    /**
     * 根据发货单id查询发货单预览信息
     * @param billId 发货单id
     * @return 发货单预览信息
     */
    @Query(value = "select new top.kindless.billtest.model.common.CommonBillPreviewWithUpdateTime(b.id,b.createTime,b.updateTime,b.statusId,s.statusName)" +
            "from BillInvoice b,Status s " +
            "where b.statusId = s.id " +
            "and b.id = :billId")
    CommonBillPreviewWithUpdateTime findInvoicePreviewByBillId(@Param("billId") String billId);

    Long countAllByStatusId(Integer statusId);

}
