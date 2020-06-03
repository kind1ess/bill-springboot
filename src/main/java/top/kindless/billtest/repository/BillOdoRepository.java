package top.kindless.billtest.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import top.kindless.billtest.model.common.CommonBillPreviewWithUpdateTime;
import top.kindless.billtest.model.dto.OdoDto;
import top.kindless.billtest.model.entity.BillOdo;

import java.util.List;

public interface BillOdoRepository extends JpaRepository<BillOdo,String> {

    /**
     * 查询出库单所有预览信息
     * @return 出库单所有预览信息
     */
    @Query(value = "select new top.kindless.billtest.model.common.CommonBillPreviewWithUpdateTime(b.id,b.createTime,b.updateTime,b.statusId,s.statusName)" +
            "from BillOdo b,Status s " +
            "where b.statusId = s.id " +
            "order by b.updateTime desc ,b.statusId desc,b.id ")
    List<CommonBillPreviewWithUpdateTime> findAllOdoPreview();

    /**
     * 查询出库单所有预览信息
     * @return 出库单所有预览信息
     */
    @Query(value = "select new top.kindless.billtest.model.common.CommonBillPreviewWithUpdateTime(b.id,b.createTime,b.updateTime,b.statusId,s.statusName)" +
            "from BillOdo b,Status s " +
            "where b.statusId = s.id " +
            "order by b.updateTime desc ,b.statusId desc,b.id ")
    List<CommonBillPreviewWithUpdateTime> findAllOdoPreview(@NonNull Pageable pageable);

    @Query(value = "select new top.kindless.billtest.model.dto.OdoDto(b.id,b.createTime,b.statusId,s.statusName,b.updateTime,b.staffId,st.name,st.telephone)" +
            "from BillOdo b,Status s,Staff st " +
            "where b.statusId = s.id " +
            "and b.staffId = st.id " +
            "and b.id = :billId ")
    OdoDto findOdoDtoByBillId(@Param("billId") String billId);

    @Query(value = "select new top.kindless.billtest.model.common.CommonBillPreviewWithUpdateTime(b.id,b.createTime,b.updateTime,b.statusId,s.statusName)" +
            "from BillOdo b,Status s " +
            "where b.statusId = s.id " +
            "and b.statusId = :statusId " +
            "order by b.updateTime desc ,b.statusId desc,b.id ")
    List<CommonBillPreviewWithUpdateTime> findOdoPreviewByStatusId(@Param("statusId") Integer statusId);

    @Query(value = "select new top.kindless.billtest.model.common.CommonBillPreviewWithUpdateTime(b.id,b.createTime,b.updateTime,b.statusId,s.statusName)" +
            "from BillOdo b,Status s " +
            "where b.statusId = s.id " +
            "and b.statusId = :statusId " +
            "order by b.updateTime desc ,b.statusId desc,b.id ")
    List<CommonBillPreviewWithUpdateTime> findOdoPreviewByStatusId(@Param("statusId") Integer statusId,@NonNull Pageable pageable);

    Long countAllByStatusId(@NonNull Integer statusId);


}
