package top.kindless.billtest.service.impl;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import top.kindless.billtest.exception.InternalServerErrorException;
import top.kindless.billtest.exception.UnAuthorizedException;
import top.kindless.billtest.model.entity.DetailCheck;
import top.kindless.billtest.repository.DetailCheckRepository;
import top.kindless.billtest.security.auth.Authentication;
import top.kindless.billtest.security.context.AuthContextHolder;
import top.kindless.billtest.service.DetailCheckService;

import java.util.List;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = "detailCheck")
public class DetailCheckServiceImpl implements DetailCheckService {

    private final DetailCheckRepository detailCheckRepository;

    public DetailCheckServiceImpl(DetailCheckRepository detailCheckRepository) {
        this.detailCheckRepository = detailCheckRepository;
    }

    @Override
    public void saveDetailCheck(DetailCheck detailCheck) {
        Authentication authentication = AuthContextHolder.getAuthContext().getAuthentication();
        if (authentication == null){
            throw new UnAuthorizedException("未授权请先登录");
        }
        detailCheckRepository.save(detailCheck);
    }

    @Override
    public void saveDetailCheckList(List<DetailCheck> detailCheckList) {
        for (DetailCheck detailCheck : detailCheckList) {
            saveDetailCheck(detailCheck);
        }
    }

    @Override
//    @CachePut(key = "#detailCheck.id")
    public DetailCheck updateDetailCheck(DetailCheck detailCheck) {
        Authentication authentication = AuthContextHolder.getAuthContext().getAuthentication();
        if (authentication == null){
            throw new UnAuthorizedException("未授权请先登录");
        }
        return detailCheckRepository.saveAndFlush(detailCheck);
    }

    @Override
    public void deleteDetailCheckById(Integer id) {
        Authentication authentication = AuthContextHolder.getAuthContext().getAuthentication();
        if (authentication == null){
            throw new UnAuthorizedException("未授权请先登录");
        }
        detailCheckRepository.deleteById(id);
    }

    @Override
    public DetailCheck findDetailCheckById(Integer id) {
        Authentication authentication = AuthContextHolder.getAuthContext().getAuthentication();
        if (authentication == null){
            throw new UnAuthorizedException("未授权请先登录");
        }
        Optional<DetailCheck> detailCheckOptional = detailCheckRepository.findById(id);
        if (!detailCheckOptional.isPresent()){
            throw new InternalServerErrorException("验收单明细数据不存在或已被删除").setErrorData(id);
        }
        return detailCheckOptional.get();
    }
}
