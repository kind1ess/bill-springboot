package top.kindless.billtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.kindless.billtest.model.entity.DetailPurchase;

import java.util.List;

public interface DetailPurchaseRepository extends JpaRepository<DetailPurchase,Integer> {


    List<DetailPurchase> findAllByBillId(String billId);
}
