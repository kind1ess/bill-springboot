package top.kindless.billtest.service;

import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import top.kindless.billtest.model.common.CheckListGoods;
import top.kindless.billtest.model.entity.DetailCheck;

import java.util.List;

public interface DetailCheckService {

    /**
     * 保存每一栏验收单明细
     * @param detailCheck
     */
    @Transactional
    void saveDetailCheck(@NonNull DetailCheck detailCheck);

    /**
     * 保存验收单明细集合，通常是用来保存一张验收单的所有明细
     * @param detailCheckList
     */
    void saveDetailCheckList(@NonNull List<DetailCheck> detailCheckList);

    /**
     * 更新验收单明细
     * @param detailCheck
     * @return
     */
    @NonNull
    @Transactional
    DetailCheck updateDetailCheck(@NonNull DetailCheck detailCheck);

    /**
     * 根据id删除验收单明细
     * @param id
     */
    @Transactional
    void deleteDetailCheckById(@NonNull Integer id);

    /**
     * 根据id查询验收单明细
     * @param id
     * @return
     */
    DetailCheck findDetailCheckById(@NonNull Integer id);

    /**
     * 根据关联单据编号集合生成并保存验收单明细
     * @param returnBillIds
     * @param purchaseBillIds
     */
    @Transactional
    void generateAndSaveDetailCheck(@NonNull String checkId,@NonNull List<String> returnBillIds,@NonNull List<String> purchaseBillIds);

    /**
     * 根据验收单id查询验收单明细信息
     * @param billId 验收单id
     * @return 验收单明细信息
     */
    List<CheckListGoods> findListGoodsByBillId(@NonNull String billId);

    /**
     * 根据验收单id查询验收单明细
     * @param billId 验收单id
     * @return 验收单明细
     */
    List<DetailCheck> findAllByBillId(@NonNull String billId);
}
