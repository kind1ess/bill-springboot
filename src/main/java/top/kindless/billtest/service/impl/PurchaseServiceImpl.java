package top.kindless.billtest.service.impl;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import top.kindless.billtest.exception.InternalServerErrorException;
import top.kindless.billtest.exception.UnAuthorizedException;
import top.kindless.billtest.model.entity.BillPurchase;
import top.kindless.billtest.model.entity.DetailPurchase;
import top.kindless.billtest.repository.BillPurchaseRepository;
import top.kindless.billtest.security.auth.Authentication;
import top.kindless.billtest.security.context.AuthContextHolder;
import top.kindless.billtest.service.DetailPurchaseService;
import top.kindless.billtest.service.PurchaseService;

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
        Authentication authentication = AuthContextHolder.getAuthContext().getAuthentication();
        if (authentication == null) {
            throw new UnAuthorizedException("未授权请先登录");
        }
        Optional<BillPurchase> billPurchaseOptional = billPurchaseRepository.findById(id);
        if (!billPurchaseOptional.isPresent()){
            throw new InternalServerErrorException("采购单表头数据不存在或已被删除").setErrorData(id);
        }
        return billPurchaseOptional.get();
    }

    @Override
    public List<BillPurchase> findBillsByStatusId(Integer statusId) {
        Authentication authentication = AuthContextHolder.getAuthContext().getAuthentication();
        if (authentication == null) {
            throw new UnAuthorizedException("未授权请先登录");
        }
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
        Authentication authentication = AuthContextHolder.getAuthContext().getAuthentication();
        if (authentication == null) {
            throw new UnAuthorizedException("未授权请先登录");
        }
        billPurchaseRepository.save(billPurchase);
    }

    @Override
    @CachePut(key = "#billPurchase.id")
    public BillPurchase updateBillPurchase(BillPurchase billPurchase) {
        Authentication authentication = AuthContextHolder.getAuthContext().getAuthentication();
        if (authentication == null) {
            throw new UnAuthorizedException("未授权请先登录");
        }
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
        return null;
    }
}
