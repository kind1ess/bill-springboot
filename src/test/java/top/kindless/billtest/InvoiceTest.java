package top.kindless.billtest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.kindless.billtest.model.entity.BillInvoice;
import top.kindless.billtest.repository.BillInvoiceRepository;
import top.kindless.billtest.utils.StatusConst;
import top.kindless.billtest.utils.UUIDUtils;

import java.util.Date;
import java.util.Optional;

@SpringBootTest
public class InvoiceTest {

    @Autowired
    BillInvoiceRepository billInvoiceRepository;

    @Test
    void test() throws InterruptedException {
//        BillInvoice billInvoice = new BillInvoice();
//        billInvoice.setStatusId(StatusConst.SHIPPED);
//        billInvoice.setId(UUIDUtils.generateInvoiceUUID());
//        billInvoice.setStaffId("staff_db4edec69a3b486d8dab0b3265c8a0f6");
//        billInvoice.setOdoId("ODO_3987b0ea201d459aa2676a15042f47c6");
//        billInvoice.setOrderId("order_ebe2426162bc47d1a00e755ba6b87419");
//        System.out.println(billInvoiceRepository.saveAndFlush(billInvoice));
        Optional<BillInvoice> billInvoiceOptional = billInvoiceRepository.findById("invoice_014b1f2fffa8452b83b349f80fd93a58");
        if (billInvoiceOptional.isPresent()){
            BillInvoice billInvoice = billInvoiceOptional.get();
            System.out.println(billInvoice);
            Thread.sleep(500L);
            billInvoice.setUpdateTime(new Date());
            System.out.println(billInvoiceRepository.saveAndFlush(billInvoice));
        }
    }
}
