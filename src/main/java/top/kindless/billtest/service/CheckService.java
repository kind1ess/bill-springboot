package top.kindless.billtest.service;

import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import top.kindless.billtest.model.entity.BillCheck;
import top.kindless.billtest.model.entity.DetailCheck;
import top.kindless.billtest.model.params.BillParams;
import top.kindless.billtest.model.vo.CheckVo;
import top.kindless.billtest.model.vo.CommonBillPreviewVo;
import top.kindless.billtest.service.common.BillService;

public interface CheckService extends BillService<BillCheck, DetailCheck> {

    /**
     * 保存验收单
     * @param billCheck
     */
    @Transactional
    void saveBillCheck(@NonNull BillCheck billCheck);

    /**
     * 根据id删除验收单
     * @param id
     */
    @Transactional
    void deleteBillCheckById(@NonNull String id);

    /**
     * 更新验收单
     * @param billCheck
     * @return
     */
    @Transactional
    BillCheck updateBillCheck(@NonNull BillCheck billCheck);

    /**
     * 生成验收单
     * @param billParams 关联单据id集合
     * @return 验收单id
     */
    @Transactional
    String generateCheck(@NonNull BillParams billParams);

    /**
     * 根据验收单id查询验收单
     * @param billId 验收单id
     * @return 验收单详情
     */
    CheckVo findCheckVoByBillId(@NonNull String billId);

    /**
     * 获取所有验收单预览信息
     * @return
     */
    CommonBillPreviewVo findAllCheckPreview();

    /**
     * 根据状态查询验收单预览信息
     * @param statusId 状态id
     * @return 预览信息
     */
    CommonBillPreviewVo findAllCheckPreview(@NonNull Integer statusId);

    /**
     * 根据状态查询验收单预览信息
     * @param statusId 状态id
     * @param size
     * @return 预览信息
     */
    CommonBillPreviewVo findAllCheckPreview(@NonNull Integer statusId,@NonNull Integer page,@NonNull Integer size);

    /**
     *
     * @param page 页码
     * @param size
     * @return 预览信息
     */
    CommonBillPreviewVo findAllCheckPreviewPageBy(@NonNull Integer page,@NonNull Integer size);

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
