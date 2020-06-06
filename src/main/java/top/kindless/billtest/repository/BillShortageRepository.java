package top.kindless.billtest.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import top.kindless.billtest.model.common.AdminBillTitle;
import top.kindless.billtest.model.common.CommonBillPreviewWithUpdateTime;
import top.kindless.billtest.model.entity.BillShortage;

import java.util.List;

public interface BillShortageRepository extends JpaRepository<BillShortage,String> {

    /**
     * 根据状态id查询
     * @param statusId
     * @return
     */
    List<BillShortage> findAllByStatusId(Integer statusId);

    @Query(value = "select new top.kindless.billtest.model.common.AdminBillTitle(b.id,b.createTime,b.statusId,st.statusName,b.updateTime,b.staffId,s.name,s.telephone)" +
            "from BillShortage b,Staff s,Status st " +
            "where b.staffId = s.id " +
            "and b.statusId = st.id " +
            "and b.id = :id")
    AdminBillTitle findShortageDtoById(@Param("id") String id);

    @Query(value = "select new top.kindless.billtest.model.common.CommonBillPreviewWithUpdateTime(b.id,b.createTime,b.updateTime,b.statusId,s.statusName)" +
            "from BillShortage b,Status s " +
            "where b.statusId = s.id " +
            "order by b.updateTime desc ,b.statusId desc ,b.id")
    List<CommonBillPreviewWithUpdateTime> findAllShortagePreviewVo();

    @Query(value = "select new top.kindless.billtest.model.common.CommonBillPreviewWithUpdateTime(b.id,b.createTime,b.updateTime,b.statusId,s.statusName)" +
            "from BillShortage b,Status s " +
            "where b.statusId = s.id " +
            "order by b.updateTime desc ,b.statusId desc ,b.id")
    List<CommonBillPreviewWithUpdateTime> findAllShortagePreviewVo(Pageable pageable);

    @Query(value = "select new top.kindless.billtest.model.common.CommonBillPreviewWithUpdateTime(b.id,b.createTime,b.updateTime,b.statusId,s.statusName)" +
            "from BillShortage b,Status s " +
            "where b.statusId = s.id " +
            "and b.statusId = :statusId " +
            "order by b.updateTime desc ,b.statusId desc ,b.id")
    List<CommonBillPreviewWithUpdateTime> findAllShortagePreviewVo(@Param("statusId") Integer statusId);

    @Query(value = "select new top.kindless.billtest.model.common.CommonBillPreviewWithUpdateTime(b.id,b.createTime,b.updateTime,b.statusId,s.statusName)" +
            "from BillShortage b,Status s " +
            "where b.statusId = s.id " +
            "and b.statusId = :statusId " +
            "order by b.updateTime desc ,b.statusId desc ,b.id")
    List<CommonBillPreviewWithUpdateTime> findAllShortagePreviewVo(@Param("statusId") Integer statusId,Pageable pageable);

    Long countAllByStatusId(Integer statusId);

    @Query(value = "select statusId from BillShortage where id = :id")
    Integer findStatusIdById(@Param("id") String id);
}
