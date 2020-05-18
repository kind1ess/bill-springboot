package top.kindless.billtest.service;

import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import top.kindless.billtest.model.entity.DetailShortage;

import java.util.List;

public interface DetailShortageService {
    /**
     * 保存缺货单明细
     * @param detailShortage
     */
    @Transactional
    void saveDetailShortage(@NonNull DetailShortage detailShortage);

    /**
     * 保存缺货单明细集合
     * @param detailShortageList
     */
    void saveDetailShortageList(@NonNull List<DetailShortage> detailShortageList);

    /**
     * 更新缺货单明细
     * @param detailShortage
     * @return
     */
    @Transactional
    DetailShortage updateDetailShortage(@NonNull DetailShortage detailShortage);

    /**
     * 根据id删除缺货单明细
     * @param id
     */
    @Transactional
    void deleteDetailShortageById(@NonNull Integer id);

    /**
     * 根据id查询缺货单明细
     * @param id
     * @return
     */
    DetailShortage findDetailShortageById(@NonNull Integer id);
}
