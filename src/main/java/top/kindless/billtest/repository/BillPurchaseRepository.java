package top.kindless.billtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import top.kindless.billtest.model.entity.BillPurchase;

import java.util.List;

public interface BillPurchaseRepository extends JpaRepository<BillPurchase,String> {

    /**
     * 根据状态id查询
     * @param id
     * @return
     */
    List<BillPurchase> findAllByStatusId(Integer id);

    /**
     * 查询采购单所有id
     * @return
     */
    @Query(value = "select id from BillPurchase")
    List<String> findAllId();
}
