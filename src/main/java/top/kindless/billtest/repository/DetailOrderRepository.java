package top.kindless.billtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import top.kindless.billtest.model.entity.DetailOrder;

import java.util.List;

public interface DetailOrderRepository extends JpaRepository<DetailOrder,Integer> {

    /**
     * 根据订单编号查询所有订单详情
     * @param billId
     * @return
     */
    List<DetailOrder> findAllByBillId(String billId);

    /**
     * 根据订单编号删除所有订单详情
     * @param billId
     */
    @Modifying
    void deleteAllByBillId(String billId);
}
