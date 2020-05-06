package top.kindless.billtest.service;

import top.kindless.billtest.model.entity.Specification;

public interface SpecificationService {

    /**
     * 根据id查询
     * @param id
     * @return
     */
    Specification findById(Integer id);
}
