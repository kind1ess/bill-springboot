package top.kindless.billtest.service;

import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import top.kindless.billtest.model.entity.BillReturn;

import java.util.List;

public interface ReturnService{

    /**
     * 保存还车单表头
     * @param billReturn
     */
    @Transactional
    void saveBillReturn(@NonNull BillReturn billReturn);

    /**
     * 更新还车单表头
     * @param billReturn
     * @return
     */
    @Transactional
    BillReturn updateBillReturn(@NonNull BillReturn billReturn);

    /**
     * 根据id删除还车单表头
     * @param id
     */
    @Transactional
    void deleteBillReturnById(@NonNull String id);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    BillReturn findBillReturnById(@NonNull String id);

    /**
     * 根据状态查询
     * @param statusId
     * @return
     */
    List<BillReturn> findAllBillReturnByStatusId(@NonNull Integer statusId);
}
