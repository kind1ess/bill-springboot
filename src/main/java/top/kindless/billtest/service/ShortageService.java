package top.kindless.billtest.service;

import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import top.kindless.billtest.model.entity.BillShortage;
import top.kindless.billtest.model.entity.DetailShortage;
import top.kindless.billtest.model.params.ShortageParams;
import top.kindless.billtest.model.vo.CommonBillPreviewVo;
import top.kindless.billtest.model.vo.ShortageVo;
import top.kindless.billtest.service.common.BillService;

public interface ShortageService extends BillService<BillShortage, DetailShortage> {

    /**
     * 保存缺货大表头
     * @param billShortage
     */
    @Transactional
    void saveBillShortage(@NonNull BillShortage billShortage);

    /**
     * 更新缺货单表头
     * @param billShortage
     * @return
     */
    @Transactional
    BillShortage updateBillShortage(@NonNull BillShortage billShortage);

    /**
     * 根据id删除缺货单表头
     * @param id
     */
    @Transactional
    void deleteBillShortageById(@NonNull String id);

    /**
     * 生成缺货单
     * @param shortageParams 缺货单信息
     * @return 缺货单编号
     */
    @NonNull
    @Transactional
    String generateBillShortage(@NonNull ShortageParams shortageParams);

    /**
     * 通过id查询
     * @param id
     * @return
     */
    ShortageVo findShortageVoById(@NonNull String id);

    /**
     * 获取所有缺货单预览信息
     * @return
     */
    CommonBillPreviewVo findAllShortagePreviewVo();

    /**
     * 获取所有缺货单预览信息
     * @return
     */
    CommonBillPreviewVo findAllShortagePreviewVo(Integer page,Integer size);

    /**
     * 获取所有缺货单预览信息
     * @return
     */
    CommonBillPreviewVo findAllShortagePreviewVo(Integer statusId);

    /**
     * 获取所有缺货单预览信息
     * @return
     */
    CommonBillPreviewVo findAllShortagePreviewVo(Integer statusId,Integer page,Integer size);

    /**
     * 获取数量
     * @return
     */
    Long getCount();

    /**
     * 获取数量
     * @return
     */
    Long getCount(@NonNull Integer statusId);
}
