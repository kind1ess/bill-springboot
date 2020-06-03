package top.kindless.billtest.service;

import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import top.kindless.billtest.model.entity.BillInvoice;
import top.kindless.billtest.model.entity.DetailInvoice;
import top.kindless.billtest.model.params.BillParams;
import top.kindless.billtest.model.vo.CommonBillPreviewVo;
import top.kindless.billtest.model.vo.InvoiceVo;
import top.kindless.billtest.service.common.BillService;


public interface InvoiceService extends BillService<BillInvoice, DetailInvoice> {

    /**
     * 一张发货单对应一张订货单，<br>
     *     可同时选择多个出库单进行发货，<br>
     *     而一张出库单可以对应多张订货单
     * @return
     */
    @NonNull
    @Transactional
    CommonBillPreviewVo generateInvoiceList(BillParams billParams);

    /**
     * 保存发货单表头
     * @param billInvoice 订货单表头
     */
    @Transactional
    void saveBillInvoice(@NonNull BillInvoice billInvoice);

    /**
     * 根据id查询发货单详情
     * @param invoiceId 发货单id
     * @return 发货单详情
     */
    InvoiceVo findInvoiceVoById(@NonNull String invoiceId);

    /**
     * 查询所有发货单预览信息
     * @return 所有发货单预览信息
     */
    CommonBillPreviewVo findAllInvoicePreviewVo();

    /**
     * 查询所有发货单预览信息
     * @return 所有发货单预览信息
     */
    CommonBillPreviewVo findAllInvoicePreviewVo(@NonNull Integer page,@NonNull Integer size);

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
