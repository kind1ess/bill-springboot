package top.kindless.billtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.kindless.billtest.model.entity.SysLogEntity;

public interface SysLogRepository extends JpaRepository<SysLogEntity,Integer> {
}
