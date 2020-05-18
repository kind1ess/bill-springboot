package top.kindless.billtest.service;

import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import top.kindless.billtest.model.entity.BillCheck;
import top.kindless.billtest.model.entity.DetailCheck;
import top.kindless.billtest.service.common.BillService;

public interface CheckService extends BillService<BillCheck, DetailCheck> {

    /**
     * 保存验收单
     * @param billCheck
     */
    @Transactional
    void saveBillCheck(@NonNull BillCheck billCheck);

    /**
     * 根据id删除验收单
     * @param id
     */
    @Transactional
    void deleteBillCheckById(@NonNull String id);

    /**
     * 更新验收单
     * @param billCheck
     * @return
     */
    @Transactional
    BillCheck updateBillCheck(@NonNull BillCheck billCheck);
}
