package top.kindless.billtest.service;

import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import top.kindless.billtest.model.entity.BillEnter;
import top.kindless.billtest.model.entity.DetailEnter;
import top.kindless.billtest.model.params.BillParams;
import top.kindless.billtest.model.vo.CommonBillPreviewVo;
import top.kindless.billtest.model.vo.EnterVo;
import top.kindless.billtest.service.common.BillService;

public interface EnterService extends BillService<BillEnter, DetailEnter> {

    /**
     * 保存入库单表头
     * @param billEnter
     */
    @Transactional
    void saveBillEnter(@NonNull BillEnter billEnter);

    /**
     * 更新入库单表头
     * @param billEnter
     * @return
     */
    @NonNull
    @Transactional
    BillEnter updateBillEnter(@NonNull BillEnter billEnter);

    /**
     * 根据id删除入库单表头
     * @param id
     */
    @Transactional
    void deleteBillEnterById(@NonNull String id);

    /**
     * 选择验收单集合生成入库单
     * @param billParams 验收单id集合
     * @return 入库单id
     */
    @Transactional
    @NonNull
    String generateEnter(@NonNull BillParams billParams);

    /**
     * 根据入库单id查询入库单详情
     * @param id 入库单id
     * @return 入库单详情
     */
    EnterVo findEnterVoById(@NonNull String id);

    /**
     * 获取所有入库单预览信息
     * @return 所有入库单预览信息
     */
    CommonBillPreviewVo findAllEnterPreview();

    /**
     * 根据状态获取所有入库单预览信息
     * @return 所有入库单预览信息
     */
    CommonBillPreviewVo findAllEnterPreview(@NonNull Integer statusId);

    /**
     *
     * @param page
     * @param size
     * @return
     */
    CommonBillPreviewVo findAllEnterPreviewPageBy(@NonNull Integer page,@NonNull Integer size);

    /**
     * 获取数量
     * @return
     */
    Long getCount();
}
