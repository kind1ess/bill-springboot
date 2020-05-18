package top.kindless.billtest.service.impl;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import top.kindless.billtest.exception.UnAuthorizedException;
import top.kindless.billtest.model.entity.BillCheck;
import top.kindless.billtest.model.entity.DetailCheck;
import top.kindless.billtest.repository.BillCheckRepository;
import top.kindless.billtest.security.auth.Authentication;
import top.kindless.billtest.security.context.AuthContextHolder;
import top.kindless.billtest.service.CheckService;

import java.util.List;

@Service
@CacheConfig(cacheNames = "BillCheck")
public class CheckServiceImpl implements CheckService {

    private final BillCheckRepository billCheckRepository;

    public CheckServiceImpl(BillCheckRepository billCheckRepository) {
        this.billCheckRepository = billCheckRepository;
    }

    @Override
    @Cacheable(key = "#id")
    public BillCheck findBillById(String id) {
        return null;
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
        Authentication authentication = AuthContextHolder.getAuthContext().getAuthentication();
        if (authentication == null){
            throw new UnAuthorizedException("未授权请先登录");
        }
        billCheckRepository.save(billCheck);
    }

    @Override
    @CacheEvict(key = "#id")
    public void deleteBillCheckById(String id) {
        Authentication authentication = AuthContextHolder.getAuthContext().getAuthentication();
        if (authentication == null){
            throw new UnAuthorizedException("未授权请先登录");
        }
        billCheckRepository.deleteById(id);
    }

    @Override
    @CachePut(key = "#billCheck")
    public BillCheck updateBillCheck(BillCheck billCheck) {
        Authentication authentication = AuthContextHolder.getAuthContext().getAuthentication();
        if (authentication == null){
            throw new UnAuthorizedException("未授权请先登录");
        }
        billCheckRepository.saveAndFlush(billCheck);
        return billCheck;
    }

    @Override
    public List<BillCheck> findAllBill() {
        return null;
    }
}
