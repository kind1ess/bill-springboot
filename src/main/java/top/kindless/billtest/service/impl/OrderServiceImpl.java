package top.kindless.billtest.service.impl;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import top.kindless.billtest.exception.BadRequestException;
import top.kindless.billtest.exception.ForbiddenException;
import top.kindless.billtest.exception.InternalServerErrorException;
import top.kindless.billtest.exception.UnAuthorizedException;
import top.kindless.billtest.model.common.ListGoods;
import top.kindless.billtest.model.dto.CartDto;
import top.kindless.billtest.model.dto.OrderDto;
import top.kindless.billtest.model.entity.*;
import top.kindless.billtest.model.vo.CartVo;
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
    private final DetailOrderRepository detailOrderRepository;
    private final BillOrderRepository billOrderRepository;
    private final CartService cartService;
    private final StatusService statusService;
    private final InventoryService inventoryService;
    private final CommodityService commodityService;
    private final SpecificationService specificationService;

    public OrderServiceImpl(UserService userService,
                            DetailOrderRepository detailOrderRepository,
                            BillOrderRepository billOrderRepository,
                            CartService cartService,
                            StatusService statusService,
                            InventoryService inventoryService,
                            CommodityService commodityService,
                            SpecificationService specificationService) {
        this.userService = userService;
        this.detailOrderRepository = detailOrderRepository;
        this.billOrderRepository = billOrderRepository;
        this.cartService = cartService;
        this.statusService = statusService;
        this.inventoryService = inventoryService;
        this.commodityService = commodityService;
        this.specificationService = specificationService;
    }


    @Override
    public List<BillOrder> findBillOrderByUserId(String userId) {
        return null;
    }

    @Override
    public OrderVo getOrderVoByCartIds(List<Integer> cartIds) {
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
        //查询出订单（获取时间戳）
        Optional<BillOrder> billOrderOptional = billOrderRepository.findById(billId);
        if (!billOrderOptional.isPresent()){
            throw new InternalServerErrorException("服务器出错了┭┮﹏┭┮");
        }
        BillOrder billOrderByQuery = billOrderOptional.get();
        //将数据库订单信息（包括订单详情信息）转换为前台接收的数据
        OrderDto orderDto = convertBillOrderToOrderDto(billOrderByQuery);
        List<ListGoods> goodsList = cartService.convertCartDtoListToGoodsList(cartDtoList);
        return new OrderVo(orderDto,goodsList);
    }

    @Override
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
        return null;
    }

    @Override
    public List<DetailOrder> findDetailBillsByBillId(String billId) {
        return detailOrderRepository.findAllByBillId(billId);
    }

    @Override
    public void deleteBillAndDetailBillsByBillId(String billId) {

    }

    @Override
    public void updateBill(BillOrder billOrder, DetailOrder detailOrder) {

    }

    @Override
    @Cacheable(keyGenerator = "myKeyGenerator")
    public OrderVo findOrderVoByBillId(String billId) {
        BillOrder order = findBillById(billId);
        List<DetailOrder> detailOrders = findDetailBillsByBillId(billId);
        return convertBillOrderAndDetailOrdersToOrderVo(order,detailOrders);
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
        billOrderRepository.save(billOrder);
        return billOrder;
    }

    /**
     * 生成并保存订单详情
     * @param cartDtoList
     * @param billId
     */
    private void generateAndSaveDetailOrders(List<CartDto> cartDtoList,String billId){
        DetailOrder detailOrder = null;
        for (CartDto cartDto : cartDtoList) {
            Integer goodsId = cartDto.getGoodsId();
            Integer amount = cartDto.getAmount();
            detailOrder = new DetailOrder();
            detailOrder.setBillId(billId);
            detailOrder.setGoodsId(goodsId);
            detailOrder.setAmount(amount);
            detailOrderRepository.save(detailOrder);
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
        Integer id = detailOrder.getId();
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
}
