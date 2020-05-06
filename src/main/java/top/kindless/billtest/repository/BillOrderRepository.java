package top.kindless.billtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.kindless.billtest.model.entity.BillOrder;

public interface BillOrderRepository extends JpaRepository<BillOrder,String> {
}
