package top.kindless.billtest.service.impl;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import top.kindless.billtest.exception.InternalServerErrorException;
import top.kindless.billtest.exception.UnAuthorizedException;
import top.kindless.billtest.model.entity.BillReturn;
import top.kindless.billtest.repository.BillReturnRepository;
import top.kindless.billtest.security.auth.Authentication;
import top.kindless.billtest.security.context.AuthContextHolder;
import top.kindless.billtest.service.ReturnService;

import java.util.List;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = "billReturn")
public class ReturnServiceImpl implements ReturnService {

    private final BillReturnRepository billReturnRepository;

    public ReturnServiceImpl(BillReturnRepository billReturnRepository) {
        this.billReturnRepository = billReturnRepository;
    }

    @Override
    public void saveBillReturn(BillReturn billReturn) {
        Authentication authentication = AuthContextHolder.getAuthContext().getAuthentication();
        if (authentication == null) {
            throw new UnAuthorizedException("未授权请先登录");
        }
        billReturnRepository.save(billReturn);
    }

    @Override
    @CachePut(key = "#billReturn.id")
    public BillReturn updateBillReturn(BillReturn billReturn) {
        Authentication authentication = AuthContextHolder.getAuthContext().getAuthentication();
        if (authentication == null) {
            throw new UnAuthorizedException("未授权请先登录");
        }
        billReturnRepository.saveAndFlush(billReturn);
        return billReturn;
    }

    @Override
    @CacheEvict(key = "#id")
    public void deleteBillReturnById(String id) {
        Authentication authentication = AuthContextHolder.getAuthContext().getAuthentication();
        if (authentication == null) {
            throw new UnAuthorizedException("未授权请先登录");
        }
        billReturnRepository.deleteById(id);
    }

    @Override
    @Cacheable(key = "#id")
    public BillReturn findBillReturnById(String id) {
        Authentication authentication = AuthContextHolder.getAuthContext().getAuthentication();
        if (authentication == null) {
            throw new UnAuthorizedException("未授权请先登录");
        }
        Optional<BillReturn> billReturnOptional = billReturnRepository.findById(id);
        if (!billReturnOptional.isPresent()){
            throw new InternalServerErrorException("还车单表头数据不存在或已被删除");
        }
        return billReturnOptional.get();
    }

    @Override
    public List<BillReturn> findAllBillReturnByStatusId(Integer statusId) {
        Authentication authentication = AuthContextHolder.getAuthContext().getAuthentication();
        if (authentication == null) {
            throw new UnAuthorizedException("未授权请先登录");
        }
        return billReturnRepository.findAllByStatusId(statusId);
    }
}
