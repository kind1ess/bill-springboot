package top.kindless.billtest.service.impl;

import org.springframework.stereotype.Service;
import top.kindless.billtest.exception.InternalServerErrorException;
import top.kindless.billtest.exception.UnAuthorizedException;
import top.kindless.billtest.model.entity.DetailPurchase;
import top.kindless.billtest.repository.DetailPurchaseRepository;
import top.kindless.billtest.security.auth.Authentication;
import top.kindless.billtest.security.context.AuthContextHolder;
import top.kindless.billtest.service.DetailPurchaseService;

import java.util.List;
import java.util.Optional;

@Service
public class DetailPurchaseServiceImpl implements DetailPurchaseService {

    private final DetailPurchaseRepository detailPurchaseRepository;

    public DetailPurchaseServiceImpl(DetailPurchaseRepository detailPurchaseRepository) {
        this.detailPurchaseRepository = detailPurchaseRepository;
    }

    @Override
    public void saveDetailPurchase(DetailPurchase detailPurchase) {
        detailPurchaseRepository.save(detailPurchase);
    }

    @Override
    public void saveDetailPurchaseList(List<DetailPurchase> detailPurchaseList) {
        detailPurchaseRepository.saveAll(detailPurchaseList);
    }

    @Override
    public DetailPurchase updateDetailPurchase(DetailPurchase detailPurchase) {
        return detailPurchaseRepository.saveAndFlush(detailPurchase);
    }

    @Override
    public void deleteDetailPurchaseById(Integer id) {
        detailPurchaseRepository.deleteById(id);
    }

    @Override
    public DetailPurchase findDetailPurchaseById(Integer id) {
        Optional<DetailPurchase> detailPurchaseOptional = detailPurchaseRepository.findById(id);
        if (!detailPurchaseOptional.isPresent()){
            throw new InternalServerErrorException("采购单明细数据不存在或已被删除").setErrorData(id);
        }
        return detailPurchaseOptional.get();
    }

    @Override
    public List<DetailPurchase> findAllByBillId(String billId) {
        return detailPurchaseRepository.findAllByBillId(billId);
    }


}
