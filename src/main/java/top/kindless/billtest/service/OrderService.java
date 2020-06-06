package top.kindless.billtest.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import top.kindless.billtest.model.dto.OrderPreviewDto;
import top.kindless.billtest.model.entity.BillOrder;
import top.kindless.billtest.model.entity.DetailOrder;
import top.kindless.billtest.model.params.VerifyParams;
import top.kindless.billtest.model.vo.OrderPreviewVo;
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
    @Transactional
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
    OrderPreviewVo findOrderTitlesByUserId();

    /**
     * 保存订单表头
     * @param billOrder
     */
    @Transactional
    void saveBillOrder(@NonNull BillOrder billOrder);

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
    OrderPreviewVo findAllOrderTitleVo();

    /**
     * 查询所有的订单预览信息
     * @return
     */
    OrderPreviewVo findAllOrderTitleVo(@NonNull Integer page,@NonNull Integer size);

    /**
     * 审核订单，更新订单的状态
     * @param verifyParams
     */
    @Transactional
    void verifyOrder(@NonNull VerifyParams verifyParams);

    /**
     * 根据状态id查询订单预览信息
     * @param statusId 状态id
     * @return 订单预览信息
     */
    OrderPreviewVo findOrderPreviewVoByStatusId(@NonNull Integer statusId);

    /**
     * 根据状态id查询订单预览信息
     * @param statusId 状态id
     * @param size
     * @return 订单预览信息
     */
    OrderPreviewVo findOrderPreviewVoByStatusId(@NonNull Integer statusId,@NonNull Integer page,@NonNull Integer size);

    /**
     * 获取订货单数量
     * @return
     */
    Long getCount();

    /**
     * 获取订货单数量
     * @return
     */
    Long getCount(Integer statusId);

}
