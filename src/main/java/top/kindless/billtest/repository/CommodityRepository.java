package top.kindless.billtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.kindless.billtest.model.entity.Commodity;

public interface CommodityRepository extends JpaRepository<Commodity,Integer> {
}
