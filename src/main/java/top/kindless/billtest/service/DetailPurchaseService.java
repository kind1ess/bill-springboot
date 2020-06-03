package top.kindless.billtest.service;

import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import top.kindless.billtest.model.entity.DetailPurchase;
import top.kindless.billtest.model.params.ShortageParam;

import java.util.List;

public interface DetailPurchaseService {

    /**
     * 保存采购单明细
     * @param detailPurchase
     */
    @Transactional
    void saveDetailPurchase(@NonNull DetailPurchase detailPurchase);

    /**
     * 保存采购单明细集合
     * @param detailPurchaseList
     */
    void saveDetailPurchaseList(@NonNull List<DetailPurchase> detailPurchaseList);

    /**
     * 更新采购单明细
     * @param detailPurchase
     * @return
     */
    @NonNull
    @Transactional
    DetailPurchase updateDetailPurchase(@NonNull DetailPurchase detailPurchase);

    /**
     * 根据id删除采购单明细
     * @param id
     */
    @Transactional
    void deleteDetailPurchaseById(@NonNull Integer id);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    DetailPurchase findDetailPurchaseById(@NonNull Integer id);

    /**
     * 根据单据id查询明细
     * @param billId 单据id
     * @return 明细
     */
    List<DetailPurchase> findAllByBillId(@NonNull String billId);

}
