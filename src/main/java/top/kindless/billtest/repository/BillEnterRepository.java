package top.kindless.billtest.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import top.kindless.billtest.model.common.CommonBillPreviewWithUpdateTime;
import top.kindless.billtest.model.dto.EnterDto;
import top.kindless.billtest.model.entity.BillEnter;

import java.util.List;

public interface BillEnterRepository extends JpaRepository<BillEnter,String> {

    /**
     * 根据状态id查询
     * @param statusId
     * @return
     */
    List<BillEnter> findAllByStatusId(Integer statusId);

    /**
     * 根据入库单id查询入库单表头信息
     * @param id 入库单id
     * @return 入库单表头信息
     */
    @Query(value = "select new top.kindless.billtest.model.dto.EnterDto(b.id,b.createTime,b.statusId,s.statusName,b.updateTime,b.staffId,st.name,st.telephone)" +
            "from BillEnter b,Status s, Staff st " +
            "where b.statusId = s.id " +
            "and b.staffId = st.id " +
            "and b.id = :id")
    EnterDto findEnterDtoById(@Param("id") String id);

    /**
     * 查询所有入库单预览信息
     * @return 所有入库单预览信息
     */
    @Query(value = "select new top.kindless.billtest.model.common.CommonBillPreviewWithUpdateTime(b.id,b.createTime,b.updateTime,b.statusId,s.statusName)" +
            "from BillEnter b,Status s " +
            "where b.statusId = s.id " +
            "order by b.updateTime desc ,s.id desc ,b.id")
    List<CommonBillPreviewWithUpdateTime> findAllEnterPreview();

    /**
     * 查询所有入库单预览信息
     * @return 所有入库单预览信息
     */
    @Query(value = "select new top.kindless.billtest.model.common.CommonBillPreviewWithUpdateTime(b.id,b.createTime,b.updateTime,b.statusId,s.statusName)" +
            "from BillEnter b,Status s " +
            "where b.statusId = s.id " +
            "order by b.updateTime desc ,s.id desc ,b.id")
    List<CommonBillPreviewWithUpdateTime> findAllEnterPreview(@NonNull Pageable pageable);

    /**
     * 根据状态查询出库单预览信息
     * @param statusId 状态id
     * @return 出库单预览信息
     */
    @Query(value = "select new top.kindless.billtest.model.common.CommonBillPreviewWithUpdateTime(b.id,b.createTime,b.updateTime,b.statusId,s.statusName)" +
            "from BillEnter b,Status s " +
            "where b.statusId = s.id " +
            "order by b.updateTime desc ,s.id desc ,b.id")
    List<CommonBillPreviewWithUpdateTime> findAllEnterPreview(@Param("statusId") Integer statusId);

    /**
     * 根据状态查询出库单预览信息
     * @param statusId 状态id
     * @return 出库单预览信息
     */
    @Query(value = "select new top.kindless.billtest.model.common.CommonBillPreviewWithUpdateTime(b.id,b.createTime,b.updateTime,b.statusId,s.statusName)" +
            "from BillEnter b,Status s " +
            "where b.statusId = s.id " +
            "order by b.updateTime desc ,s.id desc ,b.id")
    List<CommonBillPreviewWithUpdateTime> findAllEnterPreview(@Param("statusId") Integer statusId,Pageable pageable);
}
