package top.kindless.billtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.kindless.billtest.model.entity.BillCheck;

public interface BillCheckRepository extends JpaRepository<BillCheck,String> {
}
