package top.kindless.billtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.kindless.billtest.model.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department,Integer> {
}
