package top.kindless.billtest.service;

import top.kindless.billtest.model.entity.BillOrder;
import top.kindless.billtest.model.entity.DetailOrder;
import top.kindless.billtest.model.vo.OrderVo;
import top.kindless.billtest.service.common.BillService;

import java.util.List;

public interface OrderService extends BillService<BillOrder, DetailOrder> {

    /**
     * 根据用户编号查询订单表头
     * @param userId
     * @return
     */
    List<BillOrder> findBillOrderByUserId(String userId);

    /**
     * 根据购物车信息生成订单数据
     * @param cartIds
     * @return
     */
    OrderVo getOrderVoByCartIds(List<Integer> cartIds);

    /**
     * 根据订单编号查询订单详情（包括订单表头）
     * @param billId
     * @return
     */
    OrderVo findOrderVoByBillId(String billId);

}
