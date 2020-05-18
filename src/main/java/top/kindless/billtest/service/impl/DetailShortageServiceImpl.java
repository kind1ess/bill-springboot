package top.kindless.billtest.service.impl;

import org.springframework.stereotype.Service;
import top.kindless.billtest.exception.InternalServerErrorException;
import top.kindless.billtest.exception.UnAuthorizedException;
import top.kindless.billtest.model.entity.DetailShortage;
import top.kindless.billtest.repository.DetailShortageRepository;
import top.kindless.billtest.security.auth.Authentication;
import top.kindless.billtest.security.context.AuthContextHolder;
import top.kindless.billtest.service.DetailShortageService;

import java.util.List;
import java.util.Optional;

@Service
public class DetailShortageServiceImpl implements DetailShortageService {

    private final DetailShortageRepository detailShortageRepository;

    public DetailShortageServiceImpl(DetailShortageRepository detailShortageRepository) {
        this.detailShortageRepository = detailShortageRepository;
    }

    @Override
    public void saveDetailShortage(DetailShortage detailShortage) {
        Authentication authentication = AuthContextHolder.getAuthContext().getAuthentication();
        if (authentication == null){
            throw new UnAuthorizedException("未授权请先登录");
        }
        detailShortageRepository.save(detailShortage);
    }

    @Override
    public void saveDetailShortageList(List<DetailShortage> detailShortageList) {
        for (DetailShortage detailShortage : detailShortageList) {
            saveDetailShortage(detailShortage);
        }
    }

    @Override
    public DetailShortage updateDetailShortage(DetailShortage detailShortage) {
        Authentication authentication = AuthContextHolder.getAuthContext().getAuthentication();
        if (authentication == null){
            throw new UnAuthorizedException("未授权请先登录");
        }
        return detailShortageRepository.saveAndFlush(detailShortage);
    }

    @Override
    public void deleteDetailShortageById(Integer id) {
        Authentication authentication = AuthContextHolder.getAuthContext().getAuthentication();
        if (authentication == null){
            throw new UnAuthorizedException("未授权请先登录");
        }
        detailShortageRepository.deleteById(id);
    }

    @Override
    public DetailShortage findDetailShortageById(Integer id) {
        Authentication authentication = AuthContextHolder.getAuthContext().getAuthentication();
        if (authentication == null){
            throw new UnAuthorizedException("未授权请先登录");
        }
        Optional<DetailShortage> detailShortageOptional = detailShortageRepository.findById(id);
        if (!detailShortageOptional.isPresent()){
            throw new InternalServerErrorException("缺货单明细数据不存在或已被删除").setErrorData(id);
        }
        return detailShortageOptional.get();
    }
}
