package top.kindless.billtest.service.impl;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import top.kindless.billtest.exception.BadRequestException;
import top.kindless.billtest.exception.InternalServerErrorException;
import top.kindless.billtest.model.common.CheckListGoods;
import top.kindless.billtest.model.dto.CheckDto;
import top.kindless.billtest.model.entity.*;
import top.kindless.billtest.model.params.BillParams;
import top.kindless.billtest.model.vo.CheckVo;
import top.kindless.billtest.model.vo.CommonBillPreviewVo;
import top.kindless.billtest.repository.BillCheckRepository;
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
@CacheConfig(cacheNames = "BillCheck")
public class CheckServiceImpl implements CheckService {

    private final BillCheckRepository billCheckRepository;
    private final ReturnService returnService;
    private final PurchaseService purchaseService;
    private final DetailCheckService detailCheckService;

    public CheckServiceImpl(BillCheckRepository billCheckRepository,
                            ReturnService returnService,
                            PurchaseService purchaseService,
                            DetailCheckService detailCheckService) {
        this.billCheckRepository = billCheckRepository;
        this.returnService = returnService;
        this.purchaseService = purchaseService;
        this.detailCheckService = detailCheckService;
    }

    @Override
    @Cacheable(key = "#id")
    public BillCheck findBillById(String id) {
        Optional<BillCheck> billCheckOptional = billCheckRepository.findById(id);
        if (!billCheckOptional.isPresent()) {
            throw new InternalServerErrorException("验收单数据不存在或已被删除");
        }
        return billCheckOptional.get();
    }

    @Override
    public List<BillCheck> findBillsByStatusId(Integer statusId) {
        return null;
    }

    @Override
    public void deleteBillAndDetailBillsByBillId(String billId) {

    }

    @Override
    public void updateBill(BillCheck billCheck, DetailCheck detailCheck) {

    }

    @Override
    public void saveBillCheck(BillCheck billCheck) {
        billCheckRepository.save(billCheck);
    }

    @Override
    @CacheEvict(key = "#id")
    public void deleteBillCheckById(String id) {
        billCheckRepository.deleteById(id);
    }

    @Override
    @CachePut(key = "#billCheck")
    public BillCheck updateBillCheck(BillCheck billCheck) {
        return billCheckRepository.saveAndFlush(billCheck);
    }

    @Override
    public List<BillCheck> findAllBill() {
        return billCheckRepository.findAll();
    }

    @Override
    public String generateCheck(BillParams billParams) {
        Authentication authentication = AuthContextHolder.getAuthContext().getAuthentication();
        Assert.notNull(authentication,"授权信息不能为空");
        String staffId = authentication.getStaff().getId();
        List<String> billIdList = billParams.getBillIdList();
        List<String> returnBillIds = billIdList.stream()
                .filter(s -> s.startsWith("RET"))
                .collect(Collectors.toList());
        boolean allMatch1 = returnBillIds.stream()
                .map(returnService::findStatusIdByBillId)
                .allMatch(integer -> integer.equals(StatusConst.TO_BE_CHECKED));
        List<String> purchaseBillIds = billIdList.stream()
                .filter(s -> s.startsWith("PUR"))
                .collect(Collectors.toList());
        boolean allMatch2 = purchaseBillIds.stream()
                .map(purchaseService::getBillStatusId)
                .allMatch(integer -> integer.equals(StatusConst.TO_BE_CHECKED));
        if (returnBillIds.isEmpty()&&purchaseBillIds.isEmpty()){
            throw new BadRequestException("请选择正确的还车单或采购单");
        }
        if (!(allMatch1 && allMatch2)){
            throw new BadRequestException("无法验收待验收状态以外的还车单或采购单");
        }

        return generateAndSaveBillCheck(staffId,returnBillIds,purchaseBillIds);
    }

    @Override
    public CheckVo findCheckVoByBillId(String billId) {
        CheckDto checkDto = billCheckRepository.findCheckDtoByBillId(billId);
        List<CheckListGoods> listGoods = detailCheckService.findListGoodsByBillId(billId);
        return new CheckVo(checkDto,listGoods);
    }

    @Override
    public CommonBillPreviewVo findAllCheckPreview() {
        return new CommonBillPreviewVo(billCheckRepository.findAllCheckPreview());
    }

    @Override
    public CommonBillPreviewVo findAllCheckPreview(Integer statusId) {
        return new CommonBillPreviewVo(billCheckRepository.findAllCheckPreview(statusId));
    }

    @Override
    public CommonBillPreviewVo findAllCheckPreview(Integer statusId, Integer page, Integer size) {
        return new CommonBillPreviewVo(billCheckRepository.findAllCheckPreview(statusId,PageRequest.of(page,size)));
    }

    @Override
    public CommonBillPreviewVo findAllCheckPreviewPageBy(Integer page, Integer size) {
        return new CommonBillPreviewVo(billCheckRepository.findAllCheckPreview(PageRequest.of(page,size)));
    }

    @Override
    public Long getCount() {
        return billCheckRepository.count();
    }

    @Override
    public Long getCount(Integer statusId) {
        return billCheckRepository.countAllByStatusId(statusId);
    }

    private String generateAndSaveBillCheck(String staffId, List<String> returnBillIds, List<String> purchaseBillIds){
        BillCheck billCheck = new BillCheck();
        String checkUUID = BillNoUtils.generateBillId("CHK");
        billCheck.setId(checkUUID);
        billCheck.setStaffId(staffId);
        billCheck.setStatusId(StatusConst.TO_BE_ENTERED);
        saveBillCheck(billCheck);
        detailCheckService.generateAndSaveDetailCheck(checkUUID,returnBillIds,purchaseBillIds);
        return checkUUID;
    }

    @Override
    public void setBillStatus(String billId, Integer statusId) {
        if (!(statusId.equals(StatusConst.TO_BE_ENTERED)||statusId.equals(StatusConst.ENTERED))){
            throw new BadRequestException("设置状态错误").setErrorData(statusId);
        }
        BillCheck billCheck = findBillById(billId);
        billCheck.setStatusId(statusId);
        saveBillCheck(billCheck);
    }
}
