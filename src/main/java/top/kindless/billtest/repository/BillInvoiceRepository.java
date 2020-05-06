package top.kindless.billtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.kindless.billtest.model.entity.BillInvoice;

public interface BillInvoiceRepository extends JpaRepository<BillInvoice,String> {
}
