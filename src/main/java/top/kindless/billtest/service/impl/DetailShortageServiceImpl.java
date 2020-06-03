package top.kindless.billtest.service.impl;

import org.springframework.stereotype.Service;
import top.kindless.billtest.exception.InternalServerErrorException;
import top.kindless.billtest.exception.UnAuthorizedException;
import top.kindless.billtest.model.common.CommonListGoods;
import top.kindless.billtest.model.entity.DetailShortage;
import top.kindless.billtest.model.params.ShortageParam;
import top.kindless.billtest.repository.DetailShortageRepository;
import top.kindless.billtest.security.auth.Authentication;
import top.kindless.billtest.security.context.AuthContextHolder;
import top.kindless.billtest.service.DetailShortageService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DetailShortageServiceImpl implements DetailShortageService {

    private final DetailShortageRepository detailShortageRepository;

    public DetailShortageServiceImpl(DetailShortageRepository detailShortageRepository) {
        this.detailShortageRepository = detailShortageRepository;
    }

    @Override
    public void saveDetailShortage(DetailShortage detailShortage) {
        detailShortageRepository.save(detailShortage);
    }

    @Override
    public void saveDetailShortageList(List<DetailShortage> detailShortageList) {
        detailShortageRepository.saveAll(detailShortageList);
    }

    @Override
    public DetailShortage updateDetailShortage(DetailShortage detailShortage) {
        return detailShortageRepository.saveAndFlush(detailShortage);
    }

    @Override
    public void deleteDetailShortageById(Integer id) {
        detailShortageRepository.deleteById(id);
    }

    @Override
    public DetailShortage findDetailShortageById(Integer id) {
        Optional<DetailShortage> detailShortageOptional = detailShortageRepository.findById(id);
        if (!detailShortageOptional.isPresent()){
            throw new InternalServerErrorException("缺货单明细数据不存在或已被删除").setErrorData(id);
        }
        return detailShortageOptional.get();
    }

    @Override
    public void generateAndSaveDetailShortage(String billId, List<ShortageParam> params) {
        List<DetailShortage> detailShortageList = params.stream()
                .map(shortageParam -> {
                    DetailShortage detailShortage = new DetailShortage();
                    detailShortage.setBillId(billId);
                    detailShortage.setGoodsId(shortageParam.getGoodsId());
                    detailShortage.setAmount(shortageParam.getAmount());
                    return detailShortage;
                }).collect(Collectors.toList());
        saveDetailShortageList(detailShortageList);
    }

    @Override
    public List<CommonListGoods> findListGoodsByBillId(String billId) {
        return detailShortageRepository.findListGoodsByBillId(billId);
    }
}
