package top.kindless.billtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.kindless.billtest.model.entity.DetailShortage;

public interface DetailShortageRepository extends JpaRepository<DetailShortage,Integer> {
}
