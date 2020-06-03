package top.kindless.billtest.service.impl;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import top.kindless.billtest.exception.BadRequestException;
import top.kindless.billtest.exception.ForbiddenException;
import top.kindless.billtest.exception.InternalServerErrorException;
import top.kindless.billtest.exception.UnAuthorizedException;
import top.kindless.billtest.model.common.ListGoods;
import top.kindless.billtest.model.dto.CartDto;
import top.kindless.billtest.model.dto.OrderDto;
import top.kindless.billtest.model.dto.OrderPreviewDto;
import top.kindless.billtest.model.entity.*;
import top.kindless.billtest.model.params.VerifyParams;
import top.kindless.billtest.model.vo.CartVo;
import top.kindless.billtest.model.vo.OrderPreviewVo;
import top.kindless.billtest.model.vo.OrderVo;
import top.kindless.billtest.repository.BillOrderRepository;
import top.kindless.billtest.security.auth.Authentication;
import top.kindless.billtest.security.context.AuthContextHolder;
import top.kindless.billtest.service.*;
import top.kindless.billtest.utils.BillId;
import top.kindless.billtest.utils.BillNoUtils;
import top.kindless.billtest.utils.StatusConst;
import top.kindless.billtest.utils.UUIDUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@CacheConfig(cacheNames = "order")
public class OrderServiceImpl implements OrderService {

    private final BillOrderRepository billOrderRepository;
    private final CartService cartService;
    private final FdInventoryService fdInventoryService;
    private final DetailOrderService detailOrderService;

    public OrderServiceImpl(BillOrderRepository billOrderRepository,
                            CartService cartService,
                            FdInventoryService fdInventoryService,
                            DetailOrderService detailOrderService) {
        this.billOrderRepository = billOrderRepository;
        this.cartService = cartService;
        this.fdInventoryService = fdInventoryService;
        this.detailOrderService = detailOrderService;
    }


    @Override
    public List<BillOrder> findBillOrderByUserId(String userId) {
        return billOrderRepository.findAllByUserIdOrderByCreateTimeDesc(userId);
    }

    @Override
    public String getBillOrderIdByCartIds(List<Integer> cartIds) {
        Authentication authentication = AuthContextHolder.getAuthContext().getAuthentication();
        Assert.notNull(authentication,"授权信息不能为空");
        User user = authentication.getUser();
        //通过购物车id集合查询所有购物车信息
        CartVo cartVo = cartService.findAllCartByIds(cartIds);
        List<CartDto> cartDtoList = cartVo.getCartDtoList();
        //获取用户id
        String userId = user.getId();
        //通过用户id生成一个订单单据头
        String billId = generateAndSaveBillOrder(userId);
        //生成该订单的订单详情
        generateAndSaveDetailOrders(cartDtoList,billId);
//        //查询出订单（获取时间戳）
//        BillOrder billOrderByQuery = findBillById(billId);
        //将数据库订单信息（包括订单详情信息）转换为前台接收的数据
//        OrderDto orderDto = convertBillOrderToOrderDto(billOrder);
        List<ListGoods> goodsList = cartService.convertCartDtoListToGoodsList(cartDtoList);
        //修改库存
        fdInventoryService.reduceFdInventoryByOrderGoodsList(goodsList);
        //删除购物车
        cartService.deleteCartsByIds(cartIds);
        return billId;
    }

    @Override
    @Cacheable(key = "#id")
    public BillOrder findBillById(String id) {
        Authentication authentication = AuthContextHolder.getAuthContext().getAuthentication();
        if (authentication == null){
            throw new UnAuthorizedException("未授权请先登录");
        }
        Optional<BillOrder> billOrderOptional = billOrderRepository.findById(id);
        if (!billOrderOptional.isPresent()){
            throw new BadRequestException("订单"+id+"不存在或已被清除").setErrorData(id);
        }
        User user = authentication.getUser();
        BillOrder billOrder = billOrderOptional.get();
        //其他用户不能访问订单，管理员除外
        if (user !=null&&!user.getId().equals(billOrder.getUserId())){
            throw new ForbiddenException("禁止访问其他用户的订单");
        }
        return billOrder;
    }

    @Override
    public List<BillOrder> findBillsByStatusId(Integer statusId) {
        return billOrderRepository.findAllByStatusId(statusId);
    }

    @Override
    public void deleteBillAndDetailBillsByBillId(String billId) {
       if (isExist(billId)) {
           deleteBillOrderById(billId);
           detailOrderService.deleteAllDetailOrdersByBillId(billId);
       }
    }

    @Override
    public void updateBill(BillOrder billOrder, DetailOrder detailOrder) {
        updateBillOrder(billOrder);
        detailOrderService.updateDetailOrder(detailOrder);
    }

    @Override
//    @Cacheable(keyGenerator = "myKeyGenerator")
    public OrderVo findOrderVoByBillId(String billId) {
        OrderDto orderDto = billOrderRepository.findOrderDtoByBillId(billId);
        List<ListGoods> listGoodsList = detailOrderService.findAllListGoodsByBillId(billId);
        return new OrderVo(orderDto,listGoodsList);
    }

    @Override
    public OrderPreviewVo findOrderTitlesByUserId() {
        Authentication authentication = AuthContextHolder.getAuthContext().getAuthentication();
        Assert.notNull(authentication,"授权信息不能为空");
        String userId = authentication.getUser().getId();
        List<OrderPreviewDto> orderPreviewDtoList = billOrderRepository.findAllOrderPreviewDtoByUserId(userId);
        return new OrderPreviewVo(orderPreviewDtoList);
    }

    @Override
    public void saveBillOrder(BillOrder billOrder) {
        billOrderRepository.save(billOrder);
    }

    @Override
    @CacheEvict(key = "#id")
    public void deleteBillOrderById(String id) {
        Authentication authentication = AuthContextHolder.getAuthContext().getAuthentication();
        Assert.notNull(authentication,"授权信息不能为空");
        BillOrder billOrder = findBillById(id);
        if (!billOrder.getUserId().equals(authentication.getUser().getId())){
            throw new ForbiddenException("禁止删除其他用户的订单");
        }
        billOrderRepository.deleteById(id);
    }

    @Override
    @CachePut(key = "#billOrder.id")
    public BillOrder updateBillOrder(BillOrder billOrder) {
        return billOrderRepository.saveAndFlush(billOrder);
    }

    @Override
    public OrderPreviewVo findAllOrderTitleVo() {
        //查询所有订单预览信息
        List<OrderPreviewDto> orderPreviewDtoList = billOrderRepository.findAllOrderPreviewDto();
        return new OrderPreviewVo(orderPreviewDtoList);
    }

    @Override
    public OrderPreviewVo findAllOrderTitleVo(Integer page, Integer size) {
        //查询所有订单预览信息
        List<OrderPreviewDto> orderPreviewDtoList = billOrderRepository.findAllOrderPreviewDto(PageRequest.of(page,size));
        return new OrderPreviewVo(orderPreviewDtoList);
    }

    @Override
    public List<BillOrder> findAllBill() {
        return billOrderRepository.findAll(Sort.by("createTime").descending());
    }

    @Override
    public void verifyOrder(VerifyParams verifyParams) {
        String orderId = verifyParams.getBillId();
        Integer statusId = verifyParams.getStatusId();
        if (!(statusId.equals(StatusConst.FAILED)||statusId.equals(StatusConst.TO_BE_OUTBOUND))){
            throw new BadRequestException("审核操作无效，只能设置为未通过或待出库两种状态").setErrorData(statusId);
        }
        BillOrder billOrder = findBillById(orderId);
        if (!billOrder.getStatusId().equals(StatusConst.TO_BE_VERIFIED)){
            throw new BadRequestException("不能审核待审核状态订单以外的订单").setErrorData(billOrder);
        }
        billOrder.setStatusId(statusId);
        updateBillOrder(billOrder);
    }

    @Override
    public OrderPreviewVo findOrderPreviewVoByStatusId(Integer statusId) {
        return new OrderPreviewVo(billOrderRepository.findAllOrderPreviewDtoByStatusId(statusId));
    }

    @Override
    public OrderPreviewVo findOrderPreviewVoByStatusId(Integer statusId, Integer page, Integer size) {
        return new OrderPreviewVo(billOrderRepository.findAllOrderPreviewDtoByStatusId(statusId,PageRequest.of(page,size)));
    }

    @Override
    public Long getCount() {
        return billOrderRepository.count();
    }

    @Override
    public Long getCount(Integer statusId) {
        return billOrderRepository.countAllByStatusId(statusId);
    }

    @Override
    public void setBillStatus(String billId, Integer statusId) {
        BillOrder billOrder = findBillById(billId);
        if (statusId.equals(StatusConst.FAILED)){
            throw new UnAuthorizedException("您的权限不足，无法访问");
        }
        if ((statusId > 5&&statusId != 10)||statusId < 1){
            throw new BadRequestException("设置订单状态无效").setErrorData(statusId);
        }
        billOrder.setStatusId(statusId);
        saveBillOrder(billOrder);
    }

    @Override
    public Integer getBillStatusId(String billId) {
        Optional<BillOrder> billOrderOptional = billOrderRepository.findById(billId);
        if (!billOrderOptional.isPresent()){
            throw new InternalServerErrorException("数据不存在或已被删除").setErrorData(billId);
        }
        return billOrderOptional.get().getStatusId();
    }

    /**
     * 根据id判断该订单是否存在
     * @param billId 订单编号
     * @return 是否存在该订单
     */
    private boolean isExist(String billId){
        Optional<BillOrder> billOrderOptional = billOrderRepository.findById(billId);
        if (!billOrderOptional.isPresent()){
            throw new BadRequestException("不存在该订单，或已被删除").setErrorData(billId);
        }
        return true;
    }
    /**
     * 保存并返回订单表头
     * @param userId
     * @return
     */
    private String generateAndSaveBillOrder(String userId){
        BillOrder billOrder = new BillOrder();
        billOrder.setId(BillNoUtils.generateBillId("ORD"));
        billOrder.setStatusId(StatusConst.TO_BE_VERIFIED);
        billOrder.setUserId(userId);
        saveBillOrder(billOrder);
        return billOrder.getId();
    }

    /**
     * 生成并保存订单详情
     * @param cartDtoList
     * @param billId
     */
    private void generateAndSaveDetailOrders(List<CartDto> cartDtoList,String billId){
        List<DetailOrder> detailOrderList = cartDtoList.stream()
                .map(s -> {
                    Integer goodsId = s.getGoodsId();
                    Integer amount = s.getAmount();
                    return new DetailOrder(billId, goodsId, amount);
                }).collect(Collectors.toList());
        detailOrderService.saveDetailOrderList(detailOrderList);
    }

    /**
     * 根据用户id查询订单预览信息集合
     * @param userId 用户id
     * @return 订单预览信息集合
     */
    @Deprecated
    private List<OrderPreviewDto> findAllOrderPreviewDtoByUserId(@NonNull String userId){
        return billOrderRepository.findAllOrderPreviewDtoByUserId(userId);
    }

    /**
     * 查询所有订单预览信息
     * @return 所有订单预览信息
     */
    @Deprecated
    private List<OrderPreviewDto> findAllOrderPreviewDto(){
        return billOrderRepository.findAllOrderPreviewDto();
    }

    /**
     * 根据状态id查询订单预览信息集合
     * @param statusId 状态id
     * @return 订单预览信息集合
     */
    @Deprecated
    private List<OrderPreviewDto> findAllOrderPreviewDtoByStatusId(Integer statusId){
        return billOrderRepository.findAllOrderPreviewDtoByStatusId(statusId);
    }
}
