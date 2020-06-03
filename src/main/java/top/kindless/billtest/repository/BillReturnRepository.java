package top.kindless.billtest.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import top.kindless.billtest.model.common.CommonBillPreview;
import top.kindless.billtest.model.common.CommonBillPreviewWithUpdateTime;
import top.kindless.billtest.model.dto.ReturnDto;
import top.kindless.billtest.model.entity.BillReturn;
import top.kindless.billtest.model.vo.CommonBillPreviewVo;

import java.util.List;

public interface BillReturnRepository extends JpaRepository<BillReturn,String> {

    /**
     * 根据状态查询
     * @param statusId
     * @return
     */
    List<BillReturn> findAllByStatusId(Integer statusId);

    @Query(value = "select new top.kindless.billtest.model.dto.ReturnDto(b.id,b.createTime,b.statusId,s.statusName,b.orderId,bo.userId,u.account,u.telephone,u.address)" +
            "from BillReturn b,BillOrder bo,Status s,User u " +
            "where b.statusId = s.id " +
            "and b.orderId = bo.id " +
            "and bo.userId = u.id " +
            "and b.id = :billId")
    ReturnDto findReturnDtoByBillId(@Param("billId") String billId);

    /**
     * 查询所有换车单id
     * @return
     */
    @Query(value = "select id from BillReturn")
    List<String> findAllId();

    /**
     * 查询所有还车单预览信息
     * @return 还车单预览信息
     */
    @Query(value = "select new top.kindless.billtest.model.common.CommonBillPreview(b.id,b.createTime,b.statusId,s.statusName)" +
            "from BillReturn b,Status s " +
            "where b.statusId = s.id " +
            "order by b.createTime desc ,b.statusId desc ,b.id")
    List<CommonBillPreview> findAllReturnPreview();

    /**
     * 查询所有还车单预览信息
     * @return 还车单预览信息
     */
    @Query(value = "select new top.kindless.billtest.model.common.CommonBillPreview(b.id,b.createTime,b.statusId,s.statusName)" +
            "from BillReturn b,Status s " +
            "where b.statusId = s.id " +
            "order by b.createTime desc ,b.statusId desc ,b.id")
    List<CommonBillPreview> findAllReturnPreview(@NonNull Pageable pageable);

    /**
     * 根据状态查询还车单预览信息
     * @param statusId 状态id
     * @return 还车单预览信息
     */
    @Query(value = "select new top.kindless.billtest.model.common.CommonBillPreview(b.id,b.createTime,b.statusId,s.statusName)" +
            "from BillReturn b,Status s " +
            "where b.statusId = s.id and b.statusId = :statusId " +
            "order by b.createTime desc ,b.statusId desc ,b.id")
    List<CommonBillPreview> findAllReturnPreview(@Param("statusId") Integer statusId);

    /**
     * 根据状态查询还车单预览信息
     * @param statusId 状态id
     * @return 还车单预览信息
     */
    @Query(value = "select new top.kindless.billtest.model.common.CommonBillPreview(b.id,b.createTime,b.statusId,s.statusName)" +
            "from BillReturn b,Status s " +
            "where b.statusId = s.id and b.statusId = :statusId " +
            "order by b.createTime desc ,b.statusId desc ,b.id")
    List<CommonBillPreview> findAllReturnPreview(@Param("statusId") Integer statusId,Pageable pageable);

    /**
     * 根据用户id查询还车单预览信息
     * @param userId 用户id
     * @return 还车单预览信息
     */
    @Query(value = "select new top.kindless.billtest.model.common.CommonBillPreview(b.id,b.createTime,b.statusId,s.statusName)" +
            "from BillReturn b,Status s ,BillOrder bo,User u " +
            "where b.statusId = s.id " +
            "and b.orderId = bo.id " +
            "and bo.userId = u.id " +
            "and bo.userId = :userId " +
            "order by b.createTime desc ,b.statusId desc ,b.id")
    List<CommonBillPreview> findAllReturnPreview(@Param("userId") String userId);

    /**
     * 根据用户id查询还车单预览信息
     * @param userId 用户id
     * @return 还车单预览信息
     */
    @Query(value = "select new top.kindless.billtest.model.common.CommonBillPreview(b.id,b.createTime,b.statusId,s.statusName)" +
            "from BillReturn b,Status s ,BillOrder bo,User u " +
            "where b.statusId = s.id " +
            "and b.orderId = bo.id " +
            "and bo.userId = u.id " +
            "and bo.userId = :userId " +
            "order by b.createTime desc ,b.statusId desc ,b.id")
    List<CommonBillPreview> findAllReturnPreview(@Param("userId") String userId,@NonNull Pageable pageable);

    /**
     * 查询还车单状态
     * @param id 还车单id
     * @return 状态id
     */
    @Query(value = "select statusId from BillReturn where id = :id")
    Integer findStatusIdById(@Param("id") String id);

    Long countAllByStatusId(@NonNull Integer statusId);
}
