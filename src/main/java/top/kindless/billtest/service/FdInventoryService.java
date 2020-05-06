package top.kindless.billtest.service;

public interface FdInventoryService {

    /**
     * 根据货物id查询数量
     * @param id
     * @return
     */
    Integer findAmountById(Integer id);
}
