package top.kindless.billtest.service;

import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import top.kindless.billtest.model.common.CommonGoodsInfo;
import top.kindless.billtest.model.entity.Inventory;

public interface InventoryService {

    /**
     * 根据id查询
     * @param id
     * @return
     */
    Inventory findById(@NonNull Integer id);

    /**
     * 将库存信息转换为公共商品信息
     * @param inventory
     * @return
     */
    @NonNull
    CommonGoodsInfo convertInventoryToCommonGoodsInfo(@NonNull Inventory inventory);

    /**
     * 根据货物id删除货物
     * @param id must not be null
     */
    @Transactional
    void deleteInventoryById(@NonNull Integer id);

    /**
     * 更新
     * @param inventory
     * @return
     */
    @NonNull
    @Transactional
    Inventory updateInventory(@NonNull Inventory inventory);

    /**
     * 保存
     * @param inventory
     */
    @Transactional
    void saveInventory(@NonNull Inventory inventory);


}
