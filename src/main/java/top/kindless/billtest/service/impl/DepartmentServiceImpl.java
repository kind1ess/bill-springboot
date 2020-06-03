package top.kindless.billtest.service.impl;

import org.springframework.stereotype.Service;
import top.kindless.billtest.exception.InternalServerErrorException;
import top.kindless.billtest.model.entity.Department;
import top.kindless.billtest.repository.DepartmentRepository;
import top.kindless.billtest.service.DepartmentService;

import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public Department findDepartmentById(Integer id) {
        Optional<Department> departmentOptional = departmentRepository.findById(id);
        if (!departmentOptional.isPresent()){
            throw new InternalServerErrorException("不存在该部门信息或已被删除").setErrorData(id);
        }
        return departmentOptional.get();
    }
}
