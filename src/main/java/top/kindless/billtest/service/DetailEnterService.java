package top.kindless.billtest.service;

import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import top.kindless.billtest.model.entity.DetailEnter;

import java.util.List;

public interface DetailEnterService {

    /**
     * 保存入库单单个明细
     * @param detailEnter
     */
    @Transactional
    void saveDetailEnter(@NonNull DetailEnter detailEnter);

    /**
     * 保存入库单明细集合
     * @param detailEnterList
     */
    void saveDetailEnterList(@NonNull List<DetailEnter> detailEnterList);

    /**
     * 更新入库单明细
     * @param detailEnter
     * @return
     */
    @NonNull
    @Transactional
    DetailEnter updateDetailEnter(@NonNull DetailEnter detailEnter);

    /**
     * 根据id删除验收单
     * @param id
     */
    @Transactional
    void deleteDetailEnterById(@NonNull Integer id);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    DetailEnter findDetailEnterById(@NonNull Integer id);
}
