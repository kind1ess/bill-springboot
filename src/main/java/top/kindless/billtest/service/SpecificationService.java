package top.kindless.billtest.service;

import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import top.kindless.billtest.model.entity.Specification;

public interface SpecificationService {

    /**
     * 根据id查询
     * @param id
     * @return
     */
    Specification findById(@NonNull Integer id);

    /**
     * 保存specification
     * @param specification
     */
    void saveSpecification(@NonNull Specification specification);

    /**
     * 更新
     * @param specification
     * @return
     */
    @NonNull
    @Transactional
    Specification updateSpecification(@NonNull Specification specification);

    /**
     * 删除
     * @param id
     */
    @Transactional
    void deleteSpecificationById(@NonNull Integer id);
}
