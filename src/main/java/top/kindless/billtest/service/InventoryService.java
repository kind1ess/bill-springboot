package top.kindless.billtest.service;

import top.kindless.billtest.model.entity.Inventory;

public interface InventoryService {

    /**
     * 根据id查询
     * @param id
     * @return
     */
    Inventory findById(Integer id);
}
