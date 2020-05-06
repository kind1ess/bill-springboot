package top.kindless.billtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.kindless.billtest.model.entity.Staff;

public interface StaffRepository extends JpaRepository<Staff,String> {
}
