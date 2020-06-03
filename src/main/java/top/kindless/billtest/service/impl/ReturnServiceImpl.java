package top.kindless.billtest.service.impl;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import top.kindless.billtest.exception.BadRequestException;
import top.kindless.billtest.exception.ForbiddenException;
import top.kindless.billtest.exception.InternalServerErrorException;
import top.kindless.billtest.model.common.CommonListGoods;
import top.kindless.billtest.model.common.ListGoods;
import top.kindless.billtest.model.dto.ReturnDto;
import top.kindless.billtest.model.entity.BillOrder;
import top.kindless.billtest.model.entity.BillReturn;
import top.kindless.billtest.model.params.VerifyParams;
import top.kindless.billtest.model.vo.ReturnPreviewVo;
import top.kindless.billtest.model.vo.ReturnVo;
import top.kindless.billtest.repository.BillReturnRepository;
import top.kindless.billtest.security.auth.Authentication;
import top.kindless.billtest.security.context.AuthContextHolder;
import top.kindless.billtest.security.roleenum.Role;
import top.kindless.billtest.service.DetailOrderService;
import top.kindless.billtest.service.OrderService;
import top.kindless.billtest.service.ReturnService;
import top.kindless.billtest.utils.BillId;
import top.kindless.billtest.utils.BillNoUtils;
import top.kindless.billtest.utils.StatusConst;
import top.kindless.billtest.utils.UUIDUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@CacheConfig(cacheNames = "billReturn")
public class ReturnServiceImpl implements ReturnService {

    private final BillReturnRepository billReturnRepository;
    private final OrderService orderService;
    private final DetailOrderService detailOrderService;

    public ReturnServiceImpl(BillReturnRepository billReturnRepository,
                             OrderService orderService,
                             DetailOrderService detailOrderService) {
        this.billReturnRepository = billReturnRepository;
        this.orderService = orderService;
        this.detailOrderService = detailOrderService;
    }

    @Override
    public void saveBillReturn(BillReturn billReturn) {
        billReturnRepository.save(billReturn);
    }

    @Override
    @CachePut(key = "#billReturn.id")
    public BillReturn updateBillReturn(BillReturn billReturn) {
        billReturnRepository.saveAndFlush(billReturn);
        return billReturn;
    }

    @Override
    @CacheEvict(key = "#id")
    public void deleteBillReturnById(String id) {
        billReturnRepository.deleteById(id);
    }

    @Override
    @Cacheable(key = "#id")
    public BillReturn findBillReturnById(String id) {
        Optional<BillReturn> billReturnOptional = billReturnRepository.findById(id);
        if (!billReturnOptional.isPresent()){
            throw new InternalServerErrorException("还车单表头数据不存在或已被删除");
        }
        return billReturnOptional.get();
    }

    @Override
    public List<BillReturn> findAllBillReturnByStatusId(Integer statusId) {
        return billReturnRepository.findAllByStatusId(statusId);
    }

    @Override
    public String generateBillReturn(String orderId) {
        Integer statusId = orderService.getBillStatusId(orderId);
        if (!statusId.equals(StatusConst.SHIPPED)){
            throw new BadRequestException("无法归还已发货状态订单以外的订单");
        }
        Authentication authentication = AuthContextHolder.getAuthContext().getAuthentication();
        Assert.notNull(authentication,"验证信息不能为空");
        String userId = authentication.getUser().getId();
        BillOrder billOrder = orderService.findBillById(orderId);
        if (!userId.equals(billOrder.getUserId())){
            throw new ForbiddenException("禁止归还其他用户的订单");
        }
        BillReturn billReturn = new BillReturn();
        String returnUUID = BillNoUtils.generateBillId("RET");
        billReturn.setId(returnUUID);
        billReturn.setOrderId(orderId);
        billReturn.setStatusId(StatusConst.TO_BE_VERIFIED);
        orderService.setBillStatus(orderId,StatusConst.RETURNED);
        saveBillReturn(billReturn);
        return returnUUID;
    }

    @Override
    public ReturnVo findReturnVoById(String id) {
        ReturnDto returnDto = billReturnRepository.findReturnDtoByBillId(id);
        String orderId = returnDto.getOrderId();
        List<ListGoods> goodsList = detailOrderService.findAllListGoodsByBillId(orderId);
        List<CommonListGoods> commonListGoodsList = goodsList.stream()
                .map(s -> {
                    Integer sId = s.getId();
                    Integer goodsId = s.getGoodsId();
                    String commodityName = s.getCommodityName();
                    String specificationName = s.getSpecificationName();
                    Integer amount = s.getAmount();
                    return new CommonListGoods(sId, goodsId, commodityName, specificationName, amount);
                }).collect(Collectors.toList());
        return new ReturnVo(returnDto,commonListGoodsList);
    }

    @Override
    public List<String> findAllId() {
        return billReturnRepository.findAllId();
    }

    @Override
    public List<BillReturn> findAll(List<String> ids) {
        return billReturnRepository.findAllById(ids);
    }

    @Override
    public ReturnPreviewVo findAllReturnPreview() {
        return new ReturnPreviewVo(billReturnRepository.findAllReturnPreview());
    }

    @Override
    public ReturnPreviewVo findAllReturnPreviewPageBy(Integer page, Integer size) {
        return new ReturnPreviewVo(billReturnRepository.findAllReturnPreview(PageRequest.of(page,size)));
    }

    @Override
    public ReturnPreviewVo findAllReturnPreview(Integer statusId) {
        return new ReturnPreviewVo(billReturnRepository.findAllReturnPreview(statusId));
    }

    @Override
    public ReturnPreviewVo findAllReturnPreview(Integer statusId, Integer page, Integer size) {
        return new ReturnPreviewVo(billReturnRepository.findAllReturnPreview(statusId,PageRequest.of(page,size)));
    }

    @Override
    public void setStatus(String billId, Integer statusId) {
        Authentication authentication = AuthContextHolder.getAuthContext().getAuthentication();
        Assert.notNull(authentication,"授权信息不能为空");
        Integer departmentId = authentication.getStaff().getDepartmentId();
        if (statusId.equals(StatusConst.FAILED)&&!(departmentId.equals(Role.SALESPERSON.getValue())||departmentId.equals(Role.SUPER_ADMIN.getValue())||departmentId.equals(Role.MANAGER.getValue()))){
            throw new ForbiddenException("权限不足，无法继续操作");
        }
        if (statusId != 2&& statusId != 6&& statusId != 7){
            throw new BadRequestException("设置状态错误");
        }
        BillReturn billReturn = findBillReturnById(billId);
        billReturn.setStatusId(statusId);
        saveBillReturn(billReturn);
    }

    @Override
    public void verify(VerifyParams verifyParams) {
        String billId = verifyParams.getBillId();
        Integer statusIdByBillId = findStatusIdByBillId(billId);
        if (!statusIdByBillId.equals(StatusConst.TO_BE_VERIFIED)){
            throw new BadRequestException("不能审核待审核状态以外的还车单").setErrorData(billId);
        }
        setStatus(billId,verifyParams.getStatusId());
    }

    @Override
    public Integer findStatusIdByBillId(String billId) {
        return billReturnRepository.findStatusIdById(billId);
    }

    @Override
    public ReturnPreviewVo findAllReturnPreviewByUserId() {
        Authentication authentication = AuthContextHolder.getAuthContext().getAuthentication();
        Assert.notNull(authentication,"授权信息不能为空");
        String userId = authentication.getUser().getId();
        return new ReturnPreviewVo(billReturnRepository.findAllReturnPreview(userId));
    }

    @Override
    public Long getCount() {
        return billReturnRepository.count();
    }

    @Override
    public Long getCount(Integer statusId) {
        return billReturnRepository.countAllByStatusId(statusId);
    }
}
