package top.kindless.billtest.service;

import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import top.kindless.billtest.model.entity.DetailOrder;

import java.util.List;

public interface DetailOrderService {

    /**
     * 保存订单明细
     * @param detailOrder
     */
    @Transactional
    void saveDetailOrder(@NonNull DetailOrder detailOrder);

    /**
     * 保存订单明细集合
     * @param detailOrderList
     */
    void saveDetailOrderList(@NonNull List<DetailOrder> detailOrderList);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    DetailOrder findDetailOrderById(@NonNull Integer id);

    /**
     * 更新订单明细
     * @param detailOrder
     * @return
     */
    @Transactional
    DetailOrder updateDetailOrder(@NonNull DetailOrder detailOrder);

    /**
     * 根据id删除单据明细
     * @param id
     */
    @Transactional
    void deleteDetailOrderById(@NonNull Integer id);

    /**
     * 根据订单id查询所有订单明细
     * @param billId
     * @return
     */
    List<DetailOrder> findDetailOrderListByBillId(@NonNull String billId);

    /**
     * 根据订单id删除所有订单明细
     * @param billId
     */
    @Transactional
    void deleteAllDetailOrdersByBillId(@NonNull String billId);
}
