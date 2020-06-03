package top.kindless.billtest.service.impl;

import org.springframework.stereotype.Service;
import top.kindless.billtest.model.common.CommonListGoods;
import top.kindless.billtest.model.entity.DetailInvoice;
import top.kindless.billtest.repository.DetailInvoiceRepository;
import top.kindless.billtest.service.DetailInvoiceService;

import java.util.List;

@Service
public class DetailInvoiceServiceImpl implements DetailInvoiceService {

    private final DetailInvoiceRepository detailInvoiceRepository;

    public DetailInvoiceServiceImpl(DetailInvoiceRepository detailInvoiceRepository) {
        this.detailInvoiceRepository = detailInvoiceRepository;
    }

    @Override
    public void saveDetailInvoice(DetailInvoice detailInvoice) {
        detailInvoiceRepository.save(detailInvoice);
    }

    @Override
    public void saveAllDetailInvoice(List<DetailInvoice> detailInvoiceList) {
        detailInvoiceRepository.saveAll(detailInvoiceList);
    }

    @Override
    public List<DetailInvoice> findAllDetailInvoiceByInvoiceId(String invoiceId) {
        return detailInvoiceRepository.findAllByBillId(invoiceId);
    }

    @Override
    public List<CommonListGoods> findAllListGoodsByBillId(String billId) {
        return detailInvoiceRepository.findListGoodsByBillId(billId);
    }
}
