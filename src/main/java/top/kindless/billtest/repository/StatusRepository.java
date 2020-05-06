package top.kindless.billtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import top.kindless.billtest.model.entity.Status;

public interface StatusRepository extends JpaRepository<Status,Integer> {

    @Query(value = "select status_name from tb_status where id = ?1",nativeQuery = true)
    String findStatusNameById(Integer id);
}
