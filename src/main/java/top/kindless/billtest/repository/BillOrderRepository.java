package top.kindless.billtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
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

}
