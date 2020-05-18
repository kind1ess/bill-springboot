package top.kindless.billtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.kindless.billtest.model.entity.BillReturn;

import java.util.List;

public interface BillReturnRepository extends JpaRepository<BillReturn,String> {

    /**
     * 根据状态查询
     * @param statusId
     * @return
     */
    List<BillReturn> findAllByStatusId(Integer statusId);
}
