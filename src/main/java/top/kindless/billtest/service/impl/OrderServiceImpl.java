package top.kindless.billtest.service.impl;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import top.kindless.billtest.exception.BadRequestException;
import top.kindless.billtest.exception.ForbiddenException;
import top.kindless.billtest.exception.UnAuthorizedException;
import top.kindless.billtest.model.common.ListGoods;
import top.kindless.billtest.model.dto.CartDto;
import top.kindless.billtest.model.dto.OrderDto;
import top.kindless.billtest.model.dto.OrderTitleDto;
import top.kindless.billtest.model.entity.*;
import top.kindless.billtest.model.vo.CartVo;
import top.kindless.billtest.model.vo.OrderTitleVo;
import top.kindless.billtest.model.vo.OrderVo;
import top.kindless.billtest.repository.BillOrderRepository;
import top.kindless.billtest.repository.DetailOrderRepository;
import top.kindless.billtest.security.auth.Authentication;
import top.kindless.billtest.security.context.AuthContextHolder;
import top.kindless.billtest.service.*;
import top.kindless.billtest.utils.UUIDUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = "order")
public class OrderServiceImpl implements OrderService {

    private final UserService userService;
    private final BillOrderRepository billOrderRepository;
    private final CartService cartService;
    private final StatusService statusService;
    private final InventoryService inventoryService;
    private final CommodityService commodityService;
    private final SpecificationService specificationService;
    private final FdInventoryService fdInventoryService;
    private final DetailOrderService detailOrderService;

    public OrderServiceImpl(UserService userService,
                            BillOrderRepository billOrderRepository,
                            CartService cartService,
                            StatusService statusService,
                            InventoryService inventoryService,
                            CommodityService commodityService,
                            SpecificationService specificationService,
                            FdInventoryService fdInventoryService,
                            DetailOrderService detailOrderService) {
        this.userService = userService;
        this.billOrderRepository = billOrderRepository;
        this.cartService = cartService;
        this.statusService = statusService;
        this.inventoryService = inventoryService;
        this.commodityService = commodityService;
        this.specificationService = specificationService;
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
        if (authentication == null){
            throw new UnAuthorizedException("未授权请先登录");
        }
        //通过购物车id集合查询所有购物车信息
        CartVo cartVo = cartService.findAllCartByIds(cartIds);
        List<CartDto> cartDtoList = cartVo.getCartDtoList();
        //获取用户id
        String userId = authentication.getUser().getId();
        //通过用户id生成一个订单单据头
        BillOrder billOrder = generateAndSaveBillOrder(userId);
        //获取订单编号
        String billId = billOrder.getId();
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
        String userId = authentication.getUser().getId();
        Optional<BillOrder> billOrderOptional = billOrderRepository.findById(id);
        if (!billOrderOptional.isPresent()){
            throw new BadRequestException("订单"+id+"不存在或已被清除").setErrorData(id);
        }
        BillOrder billOrder = billOrderOptional.get();
        if (!userId.equals(billOrder.getUserId())){
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
    @Cacheable(keyGenerator = "myKeyGenerator")
    public OrderVo findOrderVoByBillId(String billId) {
        BillOrder order = findBillById(billId);
        List<DetailOrder> detailOrders = detailOrderService.findDetailOrderListByBillId(billId);
        return convertBillOrderAndDetailOrdersToOrderVo(order,detailOrders);
    }

    @Override
    public OrderTitleVo findOrderTitlesByUserId() {
        Authentication authentication = AuthContextHolder.getAuthContext().getAuthentication();
        if (authentication == null){
            throw new UnAuthorizedException("未授权请先登录");
        }
        String userId = authentication.getUser().getId();
        List<BillOrder> billOrderList = findBillOrderByUserId(userId);
        List<OrderTitleDto> orderTitleDtoList = new ArrayList<>();
        for (BillOrder billOrder : billOrderList) {
            OrderTitleDto orderTitleDto = convertBillOrderToOrderTitleDto(billOrder);
            orderTitleDtoList.add(orderTitleDto);
        }
        return new OrderTitleVo(orderTitleDtoList);
    }

    @Override
    public BillOrder saveBillOrder(BillOrder billOrder) {
        return billOrderRepository.saveAndFlush(billOrder);
    }

    @Override
    @CacheEvict(key = "#id")
    public void deleteBillOrderById(String id) {
        Authentication authentication = AuthContextHolder.getAuthContext().getAuthentication();
        if (authentication == null){
            throw new UnAuthorizedException("未授权请先登录");
        }
        BillOrder billOrder = findBillById(id);
        if (!billOrder.getUserId().equals(authentication.getUser().getId())){
            throw new ForbiddenException("禁止删除其他用户的订单");
        }
        billOrderRepository.deleteById(id);
    }

    @Override
    @CachePut(key = "#billOrder.id")
    public BillOrder updateBillOrder(BillOrder billOrder) {
        return saveBillOrder(billOrder);
    }

    @Override
    public OrderTitleVo findAllOrderTitleVo() {
        //查询所有订单表头信息
        List<BillOrder> billOrderList = findAllBill();
        List<OrderTitleDto> orderTitleDtoList = new ArrayList<>();
        OrderTitleDto orderTitleDto;
        for (BillOrder billOrder : billOrderList) {
            orderTitleDto = new OrderTitleDto();
            String billOrderId = billOrder.getId();
            Date createTime = billOrder.getCreateTime();
            Integer statusId = billOrder.getStatusId();
            String statusName = statusService.findStatusNameById(statusId);
            orderTitleDto.setOrderId(billOrderId);
            orderTitleDto.setCreateTime(createTime);
            orderTitleDto.setStatusId(statusId);
            orderTitleDto.setStatusName(statusName);
            orderTitleDtoList.add(orderTitleDto);
        }
        return new OrderTitleVo(orderTitleDtoList);
    }

    @Override
    public List<BillOrder> findAllBill() {
        Authentication authentication = AuthContextHolder.getAuthContext().getAuthentication();
        if (authentication == null){
            throw new UnAuthorizedException("未授权请先登录");
        }
        return billOrderRepository.findAll(Sort.by("createTime").descending());
    }

    /**
     * 根据id判断该订单是否存在
     * @param billId
     * @return
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
    private BillOrder generateAndSaveBillOrder(String userId){
        BillOrder billOrder = new BillOrder();
        billOrder.setId(UUIDUtils.generateOrderUUID());
        billOrder.setStatusId(1);
        billOrder.setUserId(userId);
        return saveBillOrder(billOrder);
    }

    /**
     * 生成并保存订单详情
     * @param cartDtoList
     * @param billId
     */
    private void generateAndSaveDetailOrders(List<CartDto> cartDtoList,String billId){
        DetailOrder detailOrder;
        for (CartDto cartDto : cartDtoList) {
            Integer goodsId = cartDto.getGoodsId();
            Integer amount = cartDto.getAmount();
            detailOrder = new DetailOrder();
            detailOrder.setBillId(billId);
            detailOrder.setGoodsId(goodsId);
            detailOrder.setAmount(amount);
            detailOrderService.saveDetailOrder(detailOrder);
        }
    }

    /**
     * 将entity转换为vo
     * @param billOrder
     * @param detailOrders
     * @return
     */
    private OrderVo convertBillOrderAndDetailOrdersToOrderVo(BillOrder billOrder,List<DetailOrder> detailOrders){
        return new OrderVo(convertBillOrderToOrderDto(billOrder),convertDetailOrdersToGoodsList(detailOrders));
    }

    /**
     * 将BillOrder转换为OrderDto
     * @param billOrder
     * @return
     */
    private OrderDto convertBillOrderToOrderDto(BillOrder billOrder){
        String billOrderId = billOrder.getId();
        Date createTime = billOrder.getCreateTime();
        String userId = billOrder.getUserId();
        Integer statusId = billOrder.getStatusId();
        String statusName = statusService.findStatusNameById(statusId);
        User user = userService.findUserById(userId);
        String userAccount = user.getAccount();
        String userTelephone = user.getTelephone();
        String userAddress = user.getAddress();
        OrderDto orderDto = new OrderDto();
        orderDto.setBillId(billOrderId);
        orderDto.setCreateTime(createTime);
        orderDto.setStatusId(statusId);
        orderDto.setStatusName(statusName);
        orderDto.setUserId(userId);
        orderDto.setUserAccount(userAccount);
        orderDto.setUserTelephone(userTelephone);
        orderDto.setUserAddress(userAddress);
        return orderDto;
    }

    /**
     * 将List<DetailOrder>转换为List<ListGoods>
     * @param detailOrders
     * @return
     */
    private List<ListGoods> convertDetailOrdersToGoodsList(List<DetailOrder> detailOrders){
        List<ListGoods> goodsList = new ArrayList<>();
        ListGoods listGoods;
        for (DetailOrder detailOrder : detailOrders) {
            listGoods = convertDetailOrderToListGoods(detailOrder);
            goodsList.add(listGoods);
        }
        return goodsList;
    }

    /**
     * 将DetailOrder转换为ListGoods
     * @param detailOrder
     * @return
     */
    private ListGoods convertDetailOrderToListGoods(DetailOrder detailOrder){
        Integer goodsId = detailOrder.getGoodsId();
        Integer amount = detailOrder.getAmount();
        Inventory inventory = inventoryService.findById(goodsId);
        Integer commodityId = inventory.getCommodityId();
        Integer specificationId = inventory.getSpecificationId();
        Commodity commodity = commodityService.findById(commodityId);
        Specification specification = specificationService.findById(specificationId);
        String commodityName = commodity.getCommodityName();
        Float price = commodity.getPrice();
        String specificationName = specification.getSpecificationName();
        ListGoods listGoods = new ListGoods();
        listGoods.setGoodsId(goodsId);
        listGoods.setCommodityName(commodityName);
        listGoods.setSpecificationName(specificationName);
        listGoods.setPrice(price);
        listGoods.setAmount(amount);
        listGoods.setSumPrice(price*amount);
        return listGoods;
    }

    private OrderTitleDto convertBillOrderToOrderTitleDto(BillOrder billOrder){
        return new OrderTitleDto(
                billOrder.getId(),
                billOrder.getCreateTime(),
                billOrder.getStatusId(),
                statusService.findStatusNameById(billOrder.getStatusId())
        );
    }
}
