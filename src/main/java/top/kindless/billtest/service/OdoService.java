package top.kindless.billtest.service;

import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import top.kindless.billtest.model.entity.BillOdo;
import top.kindless.billtest.model.entity.DetailOdo;
import top.kindless.billtest.model.params.BillParams;
import top.kindless.billtest.model.vo.CommonBillPreviewVo;
import top.kindless.billtest.model.vo.OdoVo;
import top.kindless.billtest.service.common.BillService;

public interface OdoService extends BillService<BillOdo, DetailOdo> {

    /**
     * 通过订货单集合生成出库单
     * @param billParams must not ne null
     * @return 出库单id
     */
    @Transactional
    @NonNull
    String addToOdo(@NonNull BillParams billParams);

    /**
     * 保存出库单表头
     * @param billOdo 出库单表头
     */
    @NonNull
    @Transactional
    void saveBillOdo(@NonNull BillOdo billOdo);

    /**
     * 查询所有出库单预览信息
     * @return CommonBillPreviewVo
     */
    CommonBillPreviewVo findAllOdoPreviewVo();

    /**
     * 查询所有出库单预览信息
     * @return CommonBillPreviewVo
     */
    CommonBillPreviewVo findAllOdoPreviewVo(@NonNull Integer page,@NonNull Integer size);

    /**
     * 根据id查询出库单详情
     * @param odoId 出库单id
     * @return 出库单详情
     */
    OdoVo findOdoVoById(@NonNull String odoId);

    /**
     * 根据状态id查询出库单预览信息
     * @param statusId 状态id
     * @return 出库单预览信息
     */
    CommonBillPreviewVo findOdoPreviewVoByStatusId(@NonNull Integer statusId);

    /**
     * 根据状态id查询出库单预览信息
     * @param statusId 状态id
     * @param size
     * @return 出库单预览信息
     */
    CommonBillPreviewVo findOdoPreviewVoByStatusId(@NonNull Integer statusId,@NonNull Integer page,@NonNull Integer size);

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
