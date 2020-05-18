package top.kindless.billtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.kindless.billtest.model.entity.DetailPurchase;

public interface DetailPurchaseRepository extends JpaRepository<DetailPurchase,Integer> {
}
