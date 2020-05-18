package top.kindless.billtest.service;

import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import top.kindless.billtest.model.entity.BillOrder;
import top.kindless.billtest.model.entity.DetailOrder;
import top.kindless.billtest.model.dto.OrderTitleDto;
import top.kindless.billtest.model.vo.OrderTitleVo;
import top.kindless.billtest.model.vo.OrderVo;
import top.kindless.billtest.service.common.BillService;

import java.util.List;

public interface OrderService extends BillService<BillOrder, DetailOrder> {

    /**
     * 根据用户编号查询订单表头
     * @param userId
     * @return
     */
    List<BillOrder> findBillOrderByUserId(@NonNull String userId);

    /**
     * 根据购物车信息生成订单数据
     * @param cartIds
     * @return
     */
    @NonNull
    String getBillOrderIdByCartIds(@NonNull List<Integer> cartIds);

    /**
     * 根据订单编号查询订单详情（包括订单表头）
     * @param billId
     * @return
     */
    OrderVo findOrderVoByBillId(@NonNull String billId);

    /**
     * 查询用户的所有订单表头
     * @return
     */
    OrderTitleVo findOrderTitlesByUserId();

    /**
     * 保存订单表头
     * @param billOrder
     */
    @Transactional
    BillOrder saveBillOrder(@NonNull BillOrder billOrder);

    /**
     * 根据id删除订单
     * @param id
     */
    @Transactional
    void deleteBillOrderById(@NonNull String id);

    /**
     * 更新订单
     * @param billOrder
     * @return
     */
    @Transactional
    BillOrder updateBillOrder(@NonNull BillOrder billOrder);

    /**
     * 查询所有的订单预览信息
     * @return
     */
    OrderTitleVo findAllOrderTitleVo();
}
