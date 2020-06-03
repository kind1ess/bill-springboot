package top.kindless.billtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import top.kindless.billtest.model.dto.StaffPreviewDto;
import top.kindless.billtest.model.entity.Staff;
import top.kindless.billtest.model.vo.StaffVo;

import java.util.List;

public interface StaffRepository extends JpaRepository<Staff,String> {
    /**
     * 根据账号查询员工
     * @param account
     * @return
     */
    Staff findByAccount(String account);

    /**
     * 根据id查询员工信息
     * @param staffId 员工id
     * @return 员工信息
     */
    @Query(value = "select new top.kindless.billtest.model.vo.StaffVo(s.id,s.account,s.password,s.name,s.telephone,s.departmentId,d.departmentName,d.position)" +
            "from Staff s,Department d " +
            "where s.departmentId = d.id " +
            "and s.id = :staffId")
    StaffVo findStaffVoByStaffId(@Param("staffId") String staffId);

    /**
     * 根据部门id查询
     * @param departmentId 部门id
     * @return 员工预览信息
     */
    @Query(value = "select new top.kindless.billtest.model.dto.StaffPreviewDto(s.id,s.name,s.departmentId,d.departmentName)" +
            "from Staff s,Department d " +
            "where s.departmentId = d.id " +
            "and s.departmentId = :departmentId")
    List<StaffPreviewDto> findStaffPreviewDtoByDepartmentId(@Param("departmentId") Integer departmentId);


    /**
     * 查询所有员工预览信息
     * @return 所有员工预览信息
     */
    @Query(value = "select new top.kindless.billtest.model.dto.StaffPreviewDto(s.id,s.name,s.departmentId,d.departmentName)" +
            "from Staff s,Department d " +
            "where s.departmentId = d.id")
    List<StaffPreviewDto> findAllStaffPreviewDto();

    @Query(value = "select new top.kindless.billtest.model.vo.StaffVo(s.id,s.account,s.password,s.name,s.telephone,s.departmentId,d.departmentName,d.position)" +
            "from Staff s,Department d " +
            "where s.departmentId = d.id")
    List<StaffVo> findAllStaffVo();
}
