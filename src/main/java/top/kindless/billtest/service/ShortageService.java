package top.kindless.billtest.service;

import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import top.kindless.billtest.model.entity.BillShortage;
import top.kindless.billtest.model.entity.DetailShortage;
import top.kindless.billtest.service.common.BillService;

public interface ShortageService extends BillService<BillShortage, DetailShortage> {

    /**
     * 保存缺货大表头
     * @param billShortage
     */
    @Transactional
    void saveBillShortage(@NonNull BillShortage billShortage);

    /**
     * 更新缺货单表头
     * @param billShortage
     * @return
     */
    @Transactional
    BillShortage updateBillShortage(@NonNull BillShortage billShortage);

    /**
     * 根据id删除缺货单表头
     * @param id
     */
    @Transactional
    void deleteBillShortageById(@NonNull String id);
}
