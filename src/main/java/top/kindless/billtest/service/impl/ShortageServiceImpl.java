package top.kindless.billtest.service.impl;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import top.kindless.billtest.exception.InternalServerErrorException;
import top.kindless.billtest.exception.UnAuthorizedException;
import top.kindless.billtest.model.entity.BillShortage;
import top.kindless.billtest.model.entity.DetailShortage;
import top.kindless.billtest.repository.BillShortageRepository;
import top.kindless.billtest.security.auth.Authentication;
import top.kindless.billtest.security.context.AuthContextHolder;
import top.kindless.billtest.service.DetailShortageService;
import top.kindless.billtest.service.ShortageService;

import java.util.List;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = "billShortage")
public class ShortageServiceImpl implements ShortageService {

    private final BillShortageRepository billShortageRepository;
    private final DetailShortageService detailShortageService;

    public ShortageServiceImpl(BillShortageRepository billShortageRepository,
                               DetailShortageService detailShortageService) {
        this.billShortageRepository = billShortageRepository;
        this.detailShortageService = detailShortageService;
    }

    @Override
    @Cacheable(key = "#id")
    public BillShortage findBillById(String id) {
        Authentication authentication = AuthContextHolder.getAuthContext().getAuthentication();
        if (authentication == null) {
            throw new UnAuthorizedException("未授权请先登录");
        }
        Optional<BillShortage> billShortageOptional = billShortageRepository.findById(id);
        if (!billShortageOptional.isPresent()){
            throw new InternalServerErrorException("缺货单表头单据不存在或已被删除");
        }
        return billShortageOptional.get();
    }

    @Override
    public List<BillShortage> findBillsByStatusId(Integer statusId) {
        Authentication authentication = AuthContextHolder.getAuthContext().getAuthentication();
        if (authentication == null) {
            throw new UnAuthorizedException("未授权请先登录");
        }
        return billShortageRepository.findAllByStatusId(statusId);
    }

    @Override
    public void deleteBillAndDetailBillsByBillId(String billId) {
//        Authentication authentication = AuthContextHolder.getAuthContext().getAuthentication();
//        if (authentication == null) {
//            throw new UnAuthorizedException("未授权请先登录");
//        }
//        billShortageRepository.deleteById(billId);
    }

    @Override
    public void updateBill(BillShortage billShortage, DetailShortage detailShortage) {
        updateBillShortage(billShortage);
        detailShortageService.updateDetailShortage(detailShortage);
    }

    @Override
    public void saveBillShortage(BillShortage billShortage) {
        Authentication authentication = AuthContextHolder.getAuthContext().getAuthentication();
        if (authentication == null) {
            throw new UnAuthorizedException("未授权请先登录");
        }
        billShortageRepository.save(billShortage);
    }

    @Override
    @CachePut(key = "#billShortage.id")
    public BillShortage updateBillShortage(BillShortage billShortage) {
        Authentication authentication = AuthContextHolder.getAuthContext().getAuthentication();
        if (authentication == null) {
            throw new UnAuthorizedException("未授权请先登录");
        }
        return billShortageRepository.saveAndFlush(billShortage);
    }

    @Override
    @CacheEvict(key = "#id")
    public void deleteBillShortageById(String id) {
        Authentication authentication = AuthContextHolder.getAuthContext().getAuthentication();
        if (authentication == null) {
            throw new UnAuthorizedException("未授权请先登录");
        }
        billShortageRepository.deleteById(id);
    }

    @Override
    public List<BillShortage> findAllBill() {
        return null;
    }
}
