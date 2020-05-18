package top.kindless.billtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.kindless.billtest.model.entity.BillShortage;

import java.util.List;

public interface BillShortageRepository extends JpaRepository<BillShortage,String> {

    /**
     * 根据状态id查询
     * @param statusId
     * @return
     */
    List<BillShortage> findAllByStatusId(Integer statusId);
}
