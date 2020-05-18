package top.kindless.billtest.service.impl;

import org.springframework.stereotype.Service;
import top.kindless.billtest.exception.InternalServerErrorException;
import top.kindless.billtest.exception.UnAuthorizedException;
import top.kindless.billtest.model.entity.DetailEnter;
import top.kindless.billtest.repository.DetailEnterRepository;
import top.kindless.billtest.security.auth.Authentication;
import top.kindless.billtest.security.context.AuthContextHolder;
import top.kindless.billtest.service.DetailEnterService;

import java.util.List;
import java.util.Optional;

@Service
public class DetailEnterServiceImpl implements DetailEnterService {

    private final DetailEnterRepository detailEnterRepository;

    public DetailEnterServiceImpl(DetailEnterRepository detailEnterRepository) {
        this.detailEnterRepository = detailEnterRepository;
    }

    @Override
    public void saveDetailEnter(DetailEnter detailEnter) {
        Authentication authentication = AuthContextHolder.getAuthContext().getAuthentication();
        if (authentication == null){
            throw new UnAuthorizedException("未授权请先登录");
        }
        detailEnterRepository.save(detailEnter);
    }

    @Override
    public void saveDetailEnterList(List<DetailEnter> detailEnterList) {
        for (DetailEnter detailEnter : detailEnterList) {
            saveDetailEnter(detailEnter);
        }
    }

    @Override
    public DetailEnter updateDetailEnter(DetailEnter detailEnter) {
        Authentication authentication = AuthContextHolder.getAuthContext().getAuthentication();
        if (authentication == null){
            throw new UnAuthorizedException("未授权请先登录");
        }
        return detailEnterRepository.saveAndFlush(detailEnter);
}

    @Override
    public void deleteDetailEnterById(Integer id) {
        Authentication authentication = AuthContextHolder.getAuthContext().getAuthentication();
        if (authentication == null){
            throw new UnAuthorizedException("未授权请先登录");
        }
        detailEnterRepository.deleteById(id);
    }

    @Override
    public DetailEnter findDetailEnterById(Integer id) {
        Authentication authentication = AuthContextHolder.getAuthContext().getAuthentication();
        if (authentication == null){
            throw new UnAuthorizedException("未授权请先登录");
        }
        Optional<DetailEnter> detailEnterOptional = detailEnterRepository.findById(id);
        if (!detailEnterOptional.isPresent()){
            throw new InternalServerErrorException("入库单明细数据不存在或已被删除").setErrorData(id);
        }
        return detailEnterOptional.get();
    }
}
