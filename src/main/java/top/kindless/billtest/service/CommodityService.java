package top.kindless.billtest.service;

import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import top.kindless.billtest.model.entity.Commodity;

public interface CommodityService {

    /**
     * 根据id查询
     * @param id
     * @return
     */
    Commodity findById(@NonNull Integer id);

    /**
     * 新增commodity
     * @param commodity must not be null
     */
    @Transactional
    void saveCommodity(@NonNull Commodity commodity);

    /**
     * 更新commodity
     * @param commodity must not be null
     * @return
     */
    @Transactional
    @NonNull
    Commodity updateCommodity(@NonNull Commodity commodity);

    /**
     * 删除commodity
     * @param id
     */
    @Transactional
    void deleteCommodityById(@NonNull Integer id);
}
