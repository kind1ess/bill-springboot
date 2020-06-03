package top.kindless.billtest.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import top.kindless.billtest.model.common.CommonBillPreviewWithUpdateTime;
import top.kindless.billtest.model.dto.CheckDto;
import top.kindless.billtest.model.entity.BillCheck;

import java.util.List;

public interface BillCheckRepository extends JpaRepository<BillCheck,String> {

    /**
     * 根据验收单id查询验收单表头信息
     * @param billId 验收单id
     * @return 验收单表头信息
     */
    @Query(value = "select new top.kindless.billtest.model.dto.CheckDto(b.id,b.createTime,b.statusId,s.statusName,b.updateTime,b.staffId,st.name,st.telephone)" +
            "from BillCheck b,Status s,Staff st " +
            "where b.statusId = s.id " +
            "and b.staffId = st.id " +
            "and b.id = :billId")
    CheckDto findCheckDtoByBillId(@Param("billId") String billId);

    /**
     * 查询所有验收单预览信息
     * @return 验收单预览信息
     */
    @Query(value = "select new top.kindless.billtest.model.common.CommonBillPreviewWithUpdateTime(b.id,b.createTime,b.updateTime,b.statusId,s.statusName) " +
            "from BillCheck b,Status s " +
            "where b.statusId = s.id " +
            "order by b.updateTime desc ,b.statusId desc ,b.id")
    List<CommonBillPreviewWithUpdateTime> findAllCheckPreview();

    /**
     * 查询所有验收单预览信息
     * @return 验收单预览信息
     */
    @Query(value = "select new top.kindless.billtest.model.common.CommonBillPreviewWithUpdateTime(b.id,b.createTime,b.updateTime,b.statusId,s.statusName) " +
            "from BillCheck b,Status s " +
            "where b.statusId = s.id " +
            "order by b.updateTime desc ,b.statusId desc ,b.id")
    List<CommonBillPreviewWithUpdateTime> findAllCheckPreview(Pageable pageable);

    /**
     * 根据验收单状态查询
     * @param statusId 验收单状态
     * @return 验收单预览信息
     */
    @Query(value = "select new top.kindless.billtest.model.common.CommonBillPreviewWithUpdateTime(b.id,b.createTime,b.updateTime,b.statusId,s.statusName) " +
            "from BillCheck b,Status s " +
            "where b.statusId = s.id and b.statusId = :statusId " +
            "order by b.updateTime desc ,b.statusId desc ,b.id")
    List<CommonBillPreviewWithUpdateTime> findAllCheckPreview(@Param("statusId") Integer statusId);

    /**
     * 根据验收单状态查询
     * @param statusId 验收单状态
     * @return 验收单预览信息
     */
    @Query(value = "select new top.kindless.billtest.model.common.CommonBillPreviewWithUpdateTime(b.id,b.createTime,b.updateTime,b.statusId,s.statusName) " +
            "from BillCheck b,Status s " +
            "where b.statusId = s.id and b.statusId = :statusId " +
            "order by b.updateTime desc ,b.statusId desc ,b.id")
    List<CommonBillPreviewWithUpdateTime> findAllCheckPreview(@Param("statusId") Integer statusId, Pageable pageable);

    Long countAllByStatusId(Integer statusId);
}
