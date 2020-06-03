package top.kindless.billtest.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import top.kindless.billtest.model.dto.OrderDto;
import top.kindless.billtest.model.dto.OrderPreviewDto;
import top.kindless.billtest.model.entity.BillOrder;

import java.util.List;

public interface BillOrderRepository extends JpaRepository<BillOrder,String> {

    /**
     * 根据状态id查询
     * @param statusId
     * @return
     */
    List<BillOrder> findAllByStatusId(Integer statusId);

    /**
     * 根据用户的id查询用户所有订单
     * @param userId must not be null
     * @return
     */
    List<BillOrder> findAllByUserIdOrderByCreateTimeDesc(@NonNull String userId);

    /**
     * 根据订单编号查询OrderDto
     * @param orderId 订单编号
     * @return orderDto
     */
    @Query(value = "select new top.kindless.billtest.model.dto.OrderDto(b.id,b.createTime,b.statusId,s.statusName,u.id,u.account,u.telephone,u.address)" +
            "from BillOrder b,Status s,User u " +
            "where b.statusId = s.id " +
            "and b.userId = u.id " +
            "and b.id = :orderId ")
    OrderDto findOrderDtoByBillId(@Param("orderId") String orderId);

    /**
     * 根据用户id查询订单预览信息集合
     * @param userId 用户id
     * @return 订单预览信息集合
     */
    @Query(value = "select new top.kindless.billtest.model.dto.OrderPreviewDto(b.id,b.createTime,b.statusId,s.statusName)" +
            "from BillOrder b,Status s " +
            "where b.statusId = s.id " +
            "and b.userId = :userId " +
            "order by b.createTime desc ,b.statusId desc ")
    List<OrderPreviewDto> findAllOrderPreviewDtoByUserId(@Param("userId") String userId);

    /**
     * 根据用户id查询订单预览信息集合
     * @param userId 用户id
     * @return 订单预览信息集合
     */
    @Query(value = "select new top.kindless.billtest.model.dto.OrderPreviewDto(b.id,b.createTime,b.statusId,s.statusName)" +
            "from BillOrder b,Status s " +
            "where b.statusId = s.id " +
            "and b.userId = :userId " +
            "order by b.createTime desc ,b.statusId desc ")
    List<OrderPreviewDto> findAllOrderPreviewDtoByUserId(@Param("userId") String userId, @NonNull Pageable pageable);

    /**
     * 查询所有订单预览信息
     * @return 所有订单预览信息
     */
    @Query(value = "select new top.kindless.billtest.model.dto.OrderPreviewDto(b.id,b.createTime,b.statusId,s.statusName)" +
            "from BillOrder b,Status s " +
            "where b.statusId = s.id " +
            "order by b.createTime desc ,b.statusId desc ")
    List<OrderPreviewDto> findAllOrderPreviewDto();

    /**
     * 查询所有订单预览信息
     * @return 所有订单预览信息
     */
    @Query(value = "select new top.kindless.billtest.model.dto.OrderPreviewDto(b.id,b.createTime,b.statusId,s.statusName)" +
            "from BillOrder b,Status s " +
            "where b.statusId = s.id " +
            "order by b.createTime desc ,b.statusId desc ")
    List<OrderPreviewDto> findAllOrderPreviewDto(@NonNull Pageable pageable);

    /**
     * 查询所有订单预览信息
     * @return 所有订单预览信息
     */
    @Query(value = "select new top.kindless.billtest.model.dto.OrderPreviewDto(b.id,b.createTime,b.statusId,s.statusName)" +
            "from BillOrder b,Status s " +
            "where b.statusId = s.id " +
            "and b.statusId = :statusId " +
            "order by b.createTime desc ,b.statusId desc ")
    List<OrderPreviewDto> findAllOrderPreviewDtoByStatusId(@Param("statusId") Integer statusId);

    /**
     * 查询所有订单预览信息
     * @return 所有订单预览信息
     */
    @Query(value = "select new top.kindless.billtest.model.dto.OrderPreviewDto(b.id,b.createTime,b.statusId,s.statusName)" +
            "from BillOrder b,Status s " +
            "where b.statusId = s.id " +
            "and b.statusId = :statusId " +
            "order by b.createTime desc ,b.statusId desc ")
    List<OrderPreviewDto> findAllOrderPreviewDtoByStatusId(@Param("statusId") Integer statusId,@NonNull Pageable pageable);

    Long countAllByStatusId(Integer statusId);
}
