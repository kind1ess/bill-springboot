package top.kindless.billtest.service.impl;

import org.springframework.stereotype.Service;
import top.kindless.billtest.exception.BadRequestException;
import top.kindless.billtest.exception.InternalServerErrorException;
import top.kindless.billtest.model.common.PurchaseListGoods;
import top.kindless.billtest.model.entity.BillShortage;
import top.kindless.billtest.model.entity.DetailPurchase;
import top.kindless.billtest.model.entity.DetailShortage;
import top.kindless.billtest.repository.DetailPurchaseRepository;
import top.kindless.billtest.service.DetailPurchaseService;
import top.kindless.billtest.service.DetailShortageService;
import top.kindless.billtest.service.ShortageService;
import top.kindless.billtest.utils.StatusConst;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DetailPurchaseServiceImpl implements DetailPurchaseService {

    private final DetailPurchaseRepository detailPurchaseRepository;
    private final DetailShortageService detailShortageService;
    private final ShortageService shortageService;

    public DetailPurchaseServiceImpl(DetailPurchaseRepository detailPurchaseRepository,
                                     DetailShortageService detailShortageService,
                                     ShortageService shortageService) {
        this.detailPurchaseRepository = detailPurchaseRepository;
        this.detailShortageService = detailShortageService;
        this.shortageService = shortageService;
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

    @Override
    public void generateAndSaveDetailPurchase(String billId, List<String> billIdList) {
        boolean allMatch = billIdList.stream()
                .allMatch(s -> {
                    BillShortage billById = shortageService.findBillById(s);
                    return billById.getStatusId().equals(StatusConst.TO_BE_PURCHASE);
                });
        if (!allMatch){
            throw new BadRequestException("不能将待采购状态以外的缺货单加入采购单");
        }
        List<DetailPurchase> detailPurchases = new ArrayList<>();
        billIdList.forEach(shortageId -> {
            List<DetailShortage> detailShortageList = detailShortageService.findAllDetailByBillId(shortageId);
            List<DetailPurchase> detailPurchaseList = detailShortageList.stream()
                    .map(detailShortage -> {
                        DetailPurchase detailPurchase = new DetailPurchase();
                        detailPurchase.setBillId(billId);
                        detailPurchase.setShortageId(shortageId);
                        detailPurchase.setGoodsId(detailShortage.getGoodsId());
                        detailPurchase.setAmount(detailShortage.getAmount());
                        return detailPurchase;
                    }).collect(Collectors.toList());
            shortageService.setBillStatus(shortageId,StatusConst.PURCHASED);
            detailPurchases.addAll(detailPurchaseList);
        });
        saveDetailPurchaseList(detailPurchases);
    }

    @Override
    public List<PurchaseListGoods> findListGoodsByBillId(String billId) {
        return detailPurchaseRepository.findListGoodsByBillId(billId);
    }
}
