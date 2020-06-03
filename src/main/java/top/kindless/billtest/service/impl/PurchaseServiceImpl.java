package top.kindless.billtest.service.impl;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import top.kindless.billtest.exception.BadRequestException;
import top.kindless.billtest.exception.InternalServerErrorException;
import top.kindless.billtest.exception.UnAuthorizedException;
import top.kindless.billtest.model.entity.BillPurchase;
import top.kindless.billtest.model.entity.DetailPurchase;
import top.kindless.billtest.repository.BillPurchaseRepository;
import top.kindless.billtest.security.auth.Authentication;
import top.kindless.billtest.security.context.AuthContextHolder;
import top.kindless.billtest.service.DetailPurchaseService;
import top.kindless.billtest.service.PurchaseService;
import top.kindless.billtest.utils.StatusConst;

import java.util.List;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = "billPurchase")
public class PurchaseServiceImpl implements PurchaseService {

    private final BillPurchaseRepository billPurchaseRepository;
    private final DetailPurchaseService detailPurchaseService;

    public PurchaseServiceImpl(BillPurchaseRepository billPurchaseRepository,
                               DetailPurchaseService detailPurchaseService) {
        this.billPurchaseRepository = billPurchaseRepository;
        this.detailPurchaseService = detailPurchaseService;
    }

    @Override
    @Cacheable(key = "#id")
    public BillPurchase findBillById(String id) {
        Optional<BillPurchase> billPurchaseOptional = billPurchaseRepository.findById(id);
        if (!billPurchaseOptional.isPresent()){
            throw new InternalServerErrorException("采购单表头数据不存在或已被删除").setErrorData(id);
        }
        return billPurchaseOptional.get();
    }

    @Override
    public List<BillPurchase> findBillsByStatusId(Integer statusId) {
        return billPurchaseRepository.findAllByStatusId(statusId);
    }

    @Override
    public void deleteBillAndDetailBillsByBillId(String billId) {

    }

    @Override
    public void updateBill(BillPurchase billPurchase, DetailPurchase detailPurchase) {
        updateBillPurchase(billPurchase);
        detailPurchaseService.updateDetailPurchase(detailPurchase);
    }

    @Override
    public void saveBillPurchase(BillPurchase billPurchase) {
        billPurchaseRepository.save(billPurchase);
    }

    @Override
    @CachePut(key = "#billPurchase.id")
    public BillPurchase updateBillPurchase(BillPurchase billPurchase) {
        billPurchaseRepository.saveAndFlush(billPurchase);
        return billPurchase;
    }

    @Override
    @CacheEvict(key = "#id")
    public void deleteBillPurchaseById(String id) {
        billPurchaseRepository.deleteById(id);
    }

    @Override
    public List<BillPurchase> findAllBill() {
        return billPurchaseRepository.findAll();
    }

    @Override
    public List<String> findAllId() {
        return billPurchaseRepository.findAllId();
    }

    @Override
    public List<BillPurchase> findAll(List<String> ids) {
        return billPurchaseRepository.findAll();
    }

    @Override
    public void setBillStatus(String billId, Integer statusId) {
        if (!(statusId.equals(StatusConst.TO_BE_CHECKED)||statusId.equals(StatusConst.CHECKED))){
            throw new BadRequestException("设置状态错误");
        }
        BillPurchase billPurchase = findBillById(billId);
        billPurchase.setStatusId(statusId);
        saveBillPurchase(billPurchase);
    }
}
