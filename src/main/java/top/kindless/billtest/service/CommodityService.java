package top.kindless.billtest.service;

import top.kindless.billtest.model.entity.Commodity;

public interface CommodityService {

    /**
     * 根据id查询
     * @param id
     * @return
     */
    Commodity findById(Integer id);
}
