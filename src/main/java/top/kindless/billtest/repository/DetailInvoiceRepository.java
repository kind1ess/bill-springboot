package top.kindless.billtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.kindless.billtest.model.entity.DetailInvoice;

public interface DetailInvoiceRepository extends JpaRepository<DetailInvoice,Integer> {
}
