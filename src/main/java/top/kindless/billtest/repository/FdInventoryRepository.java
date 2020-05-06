package top.kindless.billtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import top.kindless.billtest.model.entity.FdInventory;

public interface FdInventoryRepository extends JpaRepository<FdInventory,Integer> {

    @Query(value = "select cur_amount from fd_inventory where goods_id=?1",nativeQuery = true)
    Integer findAmountById(Integer id);
}
