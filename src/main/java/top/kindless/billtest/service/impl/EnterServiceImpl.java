package top.kindless.billtest.service.impl;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import top.kindless.billtest.exception.InternalServerErrorException;
import top.kindless.billtest.exception.UnAuthorizedException;
import top.kindless.billtest.model.entity.BillEnter;
import top.kindless.billtest.model.entity.DetailEnter;
import top.kindless.billtest.repository.BillEnterRepository;
import top.kindless.billtest.security.auth.Authentication;
import top.kindless.billtest.security.context.AuthContextHolder;
import top.kindless.billtest.service.DetailEnterService;
import top.kindless.billtest.service.EnterService;

import java.util.List;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = "billEnter")
public class EnterServiceImpl implements EnterService {

    private final BillEnterRepository billEnterRepository;
    private final DetailEnterService detailEnterService;

    public EnterServiceImpl(BillEnterRepository billEnterRepository,
                            DetailEnterService detailEnterService) {
        this.billEnterRepository = billEnterRepository;
        this.detailEnterService = detailEnterService;
    }

    @Override
    @Cacheable(key = "#id")
    public BillEnter findBillById(String id) {
        Authentication authentication = AuthContextHolder.getAuthContext().getAuthentication();
        if (authentication == null){
            throw new UnAuthorizedException("未授权请先登录");
        }
        Optional<BillEnter> billEnterOptional = billEnterRepository.findById(id);
        if (!billEnterOptional.isPresent()){
            throw new InternalServerErrorException("入库单表头数据不存在或已被删除");
        }
        return billEnterOptional.get();
    }

    @Override
    public List<BillEnter> findBillsByStatusId(Integer statusId) {
        Authentication authentication = AuthContextHolder.getAuthContext().getAuthentication();
        if (authentication == null) {
            throw new UnAuthorizedException("未授权请先登录");
        }
        return billEnterRepository.findAllByStatusId(statusId);
    }

    @Override
    public void deleteBillAndDetailBillsByBillId(String billId) {

    }

    @Override
    public void updateBill(BillEnter billEnter, DetailEnter detailEnter) {
        Authentication authentication = AuthContextHolder.getAuthContext().getAuthentication();
        if (authentication == null) {
            throw new UnAuthorizedException("未授权请先登录");
        }
        billEnterRepository.saveAndFlush(billEnter);
        detailEnterService.updateDetailEnter(detailEnter);
    }

    @Override
    public void saveBillEnter(BillEnter billEnter) {
        Authentication authentication = AuthContextHolder.getAuthContext().getAuthentication();
        if (authentication == null) {
            throw new UnAuthorizedException("未授权请先登录");
        }
        billEnterRepository.save(billEnter);
    }

    @Override
    public BillEnter updateBillEnter(BillEnter billEnter) {
        Authentication authentication = AuthContextHolder.getAuthContext().getAuthentication();
        if (authentication == null) {
            throw new UnAuthorizedException("未授权请先登录");
        }
        billEnterRepository.saveAndFlush(billEnter);
        return billEnter;
    }

    @Override
    public void deleteBillEnterById(String id) {
        Authentication authentication = AuthContextHolder.getAuthContext().getAuthentication();
        if (authentication == null) {
            throw new UnAuthorizedException("未授权请先登录");
        }
        billEnterRepository.deleteById(id);
    }

    @Override
    public List<BillEnter> findAllBill() {
        return null;
    }
}
