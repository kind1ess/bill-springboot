package top.kindless.billtest.service;

import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import top.kindless.billtest.model.entity.BillPurchase;
import top.kindless.billtest.model.entity.DetailPurchase;
import top.kindless.billtest.service.common.BillService;

public interface PurchaseService extends BillService<BillPurchase, DetailPurchase> {

    /**
     * 保存采购单表头
     * @param billPurchase
     */
    @Transactional
    void saveBillPurchase(@NonNull BillPurchase billPurchase);

    /**
     * 更新采购单表头
     * @param billPurchase
     * @return
     */
    @Transactional
    BillPurchase updateBillPurchase(@NonNull BillPurchase billPurchase);

    /**
     * 根据id删除采购单表头
     * @param id
     */
    @Transactional
    void deleteBillPurchaseById(@NonNull String id);
}
