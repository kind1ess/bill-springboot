package top.kindless.billtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.kindless.billtest.model.entity.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory,Integer> {
}
