package top.kindless.billtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.kindless.billtest.model.entity.DetailOrder;

import java.util.List;

public interface DetailOrderRepository extends JpaRepository<DetailOrder,Integer> {

    List<DetailOrder> findAllByBillId(String billId);

}
