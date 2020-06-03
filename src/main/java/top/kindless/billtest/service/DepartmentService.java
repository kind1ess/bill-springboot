package top.kindless.billtest.service;

import org.springframework.lang.NonNull;
import top.kindless.billtest.model.entity.Department;

public interface DepartmentService {

    /**
     * 根据id查询部门信息
     * @param id
     * @return
     */
    Department findDepartmentById(@NonNull Integer id);
}
