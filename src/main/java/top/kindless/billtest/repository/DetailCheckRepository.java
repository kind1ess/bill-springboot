package top.kindless.billtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.kindless.billtest.model.entity.DetailCheck;

public interface DetailCheckRepository extends JpaRepository<DetailCheck,Integer> {
}
