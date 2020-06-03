package top.kindless.billtest.service;

import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import top.kindless.billtest.model.common.ListGoods;
import top.kindless.billtest.model.entity.FdInventory;
import top.kindless.billtest.model.vo.GoodsVo;

import java.util.List;

public interface FdInventoryService {

    /**
     * 根据货物id查询数量
     * @param id
     * @return
     */
    @NonNull
    Integer findAmountById(@NonNull Integer id);

    /**
     * 根据订单减少前台库存
     * @param goodsList
     */
    @Transactional
    void reduceFdInventoryByOrderGoodsList(@NonNull List<ListGoods> goodsList);

    /**
     * 获取所有商品信息
     * @return
     */
    GoodsVo findAllFdGoods();

    /**
     * 通过id查询
     * @param id
     * @return
     */
    FdInventory findById(@NonNull Integer id);

    /**
     * 通过货物id查询
     * @param goodsId
     * @return
     */
    FdInventory findByGoodsId(@NonNull Integer goodsId);

    /**
     * 更新
     * @param fdInventory
     * @return
     */
    @Transactional
    FdInventory updateFdInventory(@NonNull FdInventory fdInventory);

    /**
     * 保存
     * @param fdInventory
     */
    @Transactional
    void saveFdInventory(@NonNull FdInventory fdInventory);

    /**
     * 根据id删除
     * @param id
     */
    @Transactional
    void deleteById(@NonNull Integer id);
}
