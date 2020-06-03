package top.kindless.billtest.service;

import org.springframework.lang.NonNull;
import top.kindless.billtest.model.common.CommonListGoods;
import top.kindless.billtest.model.entity.DetailInvoice;

import java.util.List;

public interface DetailInvoiceService {

    /**
     * 保存发货单明细
     * @param detailInvoice 发货单明细
     */
    void saveDetailInvoice(@NonNull DetailInvoice detailInvoice);

    /**
     * 保存所有发货单明细
     * @param detailInvoiceList 发货单明细集合
     */
    void saveAllDetailInvoice(@NonNull List<DetailInvoice> detailInvoiceList);

    /**
     * 根据发货单id查询所有发货单明细集合
     * @param invoiceId 发货单id
     * @return 发货单明细集合
     */
    List<DetailInvoice> findAllDetailInvoiceByInvoiceId(@NonNull String invoiceId);

    /**
     * 根据发货单id查询发货单明细
     * @param billId 发货单id
     * @return 发货单明细
     */
    List<CommonListGoods> findAllListGoodsByBillId(@NonNull String billId);
}
