package top.kindless.billtest.service.common;


import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import top.kindless.billtest.exception.InternalServerErrorException;

import java.util.List;

/**
 * 公共单据服务<br>
 * B是单据     BillXXX<br>
 * D是单据详情   DetailXXX
 */
public interface BillService<B,D> {
    /**
     * 根据单据编号查询单据表头
     * @param id
     * @return
     */
    B findBillById(@NonNull String id);

    /**
     * 根据状态查询单据
     * @param statusId
     * @return
     */
    List<B> findBillsByStatusId(@NonNull Integer statusId);
//
//    /**
//     * 根据单据编号查询单据详情
//     * @param billId
//     * @return
//     */
//    List<D> findDetailBillsByBillId(@NonNull String billId);

    /**
     * 根据单据编号删除单据
     * @param billId
     */
    @Transactional
    void deleteBillAndDetailBillsByBillId(@NonNull String billId);

    /**
     * 修改单据
     * @param b
     * @param d
     */
    @Transactional
    void updateBill(@NonNull B b, @NonNull D d);

    /**
     * 查询所有订单表头信息
     * @return
     */
    List<B> findAllBill();

    /**
     * 更改订单状态
     * @param billId   单据id
     * @param statusId 状态id
     */
    @Transactional
    default void setBillStatus(@NonNull String billId, @NonNull Integer statusId) {
        throw new InternalServerErrorException("不能更改单据状态");
    }

    /**
     * 获取单据当前状态
     * @param billId 单据id
     * @return 状态id
     */
    default Integer getBillStatusId(@NonNull String billId) {
        return null;
    }
}
