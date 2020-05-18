package top.kindless.billtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.kindless.billtest.model.entity.BillEnter;

import java.util.List;

public interface BillEnterRepository extends JpaRepository<BillEnter,String> {

    /**
     * 根据状态id查询
     * @param statusId
     * @return
     */
    List<BillEnter> findAllByStatusId(Integer statusId);
}
