package top.kindless.billtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.kindless.billtest.model.entity.Specification;

public interface SpecificationRepository extends JpaRepository<Specification,Integer> {
}
