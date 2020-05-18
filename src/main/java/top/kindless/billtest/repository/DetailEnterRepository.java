package top.kindless.billtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.kindless.billtest.model.entity.DetailEnter;

public interface DetailEnterRepository extends JpaRepository<DetailEnter,Integer> {
}
