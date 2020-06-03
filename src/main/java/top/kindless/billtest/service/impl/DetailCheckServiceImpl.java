package top.kindless.billtest.service.impl;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import top.kindless.billtest.exception.InternalServerErrorException;
import top.kindless.billtest.model.common.CheckListGoods;
import top.kindless.billtest.model.entity.BillReturn;
import top.kindless.billtest.model.entity.DetailCheck;
import top.kindless.billtest.model.entity.DetailOrder;
import top.kindless.billtest.model.entity.DetailPurchase;
import top.kindless.billtest.repository.DetailCheckRepository;
import top.kindless.billtest.service.*;
import top.kindless.billtest.utils.StatusConst;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@CacheConfig(cacheNames = "detailCheck")
public class DetailCheckServiceImpl implements DetailCheckService {

    private final DetailCheckRepository detailCheckRepository;
    private final ReturnService returnService;
    private final DetailOrderService detailOrderService;
    private final DetailPurchaseService detailPurchaseService;
    private final PurchaseService purchaseService;

    public DetailCheckServiceImpl(DetailCheckRepository detailCheckRepository,
                                  ReturnService returnService,
                                  DetailOrderService detailOrderService,
                                  DetailPurchaseService detailPurchaseService,
                                  PurchaseService purchaseService) {
        this.detailCheckRepository = detailCheckRepository;
        this.returnService = returnService;
        this.detailOrderService = detailOrderService;
        this.detailPurchaseService = detailPurchaseService;
        this.purchaseService = purchaseService;
    }

    @Override
    public void saveDetailCheck(DetailCheck detailCheck) {
        detailCheckRepository.save(detailCheck);
    }

    @Override
    public void saveDetailCheckList(List<DetailCheck> detailCheckList) {
        detailCheckRepository.saveAll(detailCheckList);
    }

    @Override
//    @CachePut(key = "#detailCheck.id")
    public DetailCheck updateDetailCheck(DetailCheck detailCheck) {
        return detailCheckRepository.saveAndFlush(detailCheck);
    }

    @Override
    public void deleteDetailCheckById(Integer id) {
        detailCheckRepository.deleteById(id);
    }

    @Override
    public DetailCheck findDetailCheckById(Integer id) {
        Optional<DetailCheck> detailCheckOptional = detailCheckRepository.findById(id);
        if (!detailCheckOptional.isPresent()){
            throw new InternalServerErrorException("验收单明细数据不存在或已被删除").setErrorData(id);
        }
        return detailCheckOptional.get();
    }

    @Override
    public void generateAndSaveDetailCheck(String checkId,List<String> returnBillIds, List<String> purchaseBillIds) {
        List<BillReturn> billReturnList = returnService.findAll(returnBillIds);
        List<DetailCheck> detailCheckList = new ArrayList<>();
        for (BillReturn billReturn : billReturnList) {
            String returnId = billReturn.getId();
            String orderId = billReturn.getOrderId();
            List<DetailOrder> detailOrderList = detailOrderService.findDetailOrderListByBillId(orderId);
            List<DetailCheck> detailChecks = detailOrderList.stream()
                    .map(detailOrder -> {
                        Integer goodsId = detailOrder.getGoodsId();
                        Integer amount = detailOrder.getAmount();
                        DetailCheck detailCheck = new DetailCheck();
                        detailCheck.setBillId(checkId);
                        detailCheck.setRefBillId(returnId);
                        detailCheck.setGoodsId(goodsId);
                        detailCheck.setAmount(amount);
                        detailCheck.setActualAmount(amount);
                        detailCheck.setRemark("关联还车单");
                        return detailCheck;
                    }).collect(Collectors.toList());
            detailCheckList.addAll(detailChecks);
        }
        for (String purchaseBillId : purchaseBillIds) {
            List<DetailPurchase> detailPurchaseList = detailPurchaseService.findAllByBillId(purchaseBillId);
            List<DetailCheck> detailCheckList1 = detailPurchaseList.stream()
                    .map(detailPurchase -> {
                        Integer goodsId = detailPurchase.getGoodsId();
                        Integer amount = detailPurchase.getAmount();
                        DetailCheck detailCheck = new DetailCheck();
                        detailCheck.setBillId(checkId);
                        detailCheck.setRefBillId(purchaseBillId);
                        detailCheck.setGoodsId(goodsId);
                        detailCheck.setAmount(amount);
                        detailCheck.setActualAmount(amount);
                        detailCheck.setRemark("关联采购单");
                        return detailCheck;
                    }).collect(Collectors.toList());
            detailCheckList.addAll(detailCheckList1);
        }
        detailCheckRepository.saveAll(detailCheckList);
        for (String returnBillId : returnBillIds) {
            returnService.setStatus(returnBillId, StatusConst.CHECKED);
        }
        for (String purchaseBillId : purchaseBillIds) {
            purchaseService.setBillStatus(purchaseBillId,StatusConst.CHECKED);
        }
    }

    @Override
    public List<CheckListGoods> findListGoodsByBillId(String billId) {
        return detailCheckRepository.findListGoodsByBillId(billId);
    }

    @Override
    public List<DetailCheck> findAllByBillId(String billId) {
        return detailCheckRepository.findAllByBillId(billId);
    }
}
