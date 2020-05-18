package top.kindless.billtest.service;

import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import top.kindless.billtest.model.entity.BillEnter;
import top.kindless.billtest.model.entity.DetailEnter;
import top.kindless.billtest.service.common.BillService;

public interface EnterService extends BillService<BillEnter, DetailEnter> {

    /**
     * 保存入库单表头
     * @param billEnter
     */
    @Transactional
    void saveBillEnter(@NonNull BillEnter billEnter);

    /**
     * 更新入库单表头
     * @param billEnter
     * @return
     */
    @NonNull
    @Transactional
    BillEnter updateBillEnter(@NonNull BillEnter billEnter);

    /**
     * 根据id删除入库单表头
     * @param id
     */
    @Transactional
    void deleteBillEnterById(@NonNull String id);
}
