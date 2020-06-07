package top.kindless.billtest.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.kindless.billtest.config.properties.KindlessProperties;
import top.kindless.billtest.exception.*;
import top.kindless.billtest.model.dto.StaffPreviewDto;
import top.kindless.billtest.model.entity.Department;
import top.kindless.billtest.model.entity.Staff;
import top.kindless.billtest.model.params.LoginParams;
import top.kindless.billtest.model.vo.StaffPreviewVo;
import top.kindless.billtest.model.vo.StaffVo;
import top.kindless.billtest.repository.StaffRepository;
import top.kindless.billtest.security.auth.Authentication;
import top.kindless.billtest.security.context.AuthContextHolder;
import top.kindless.billtest.security.token.AuthToken;
import top.kindless.billtest.service.AdminService;
import top.kindless.billtest.service.DepartmentService;
import top.kindless.billtest.utils.HttpUtils;
import top.kindless.billtest.utils.UUIDUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;


@Service
@Slf4j
public class AdminServiceImpl implements AdminService {

    private final StaffRepository staffRepository;
    private final RedisTemplate<String,Object> redisTemplate;
    private final KindlessProperties kindlessProperties;

    public AdminServiceImpl(StaffRepository staffRepository,
                            RedisTemplate<String, Object> redisTemplate,
                            KindlessProperties kindlessProperties) {
        this.staffRepository = staffRepository;
        this.redisTemplate = redisTemplate;
        this.kindlessProperties = kindlessProperties;
    }

    @Override
    public AuthToken adminLogin(LoginParams loginParams) { ;
        String message = "用户名或密码错误";
        String account = loginParams.getAccount();
        Staff staff = staffRepository.findByAccount(account);
        if (staff == null){
            throw new LoginParamsErrorException(message).setErrorData(loginParams);
        }
        if (!staff.getPassword().equals(loginParams.getPassword())){
            throw new LoginParamsErrorException(message).setErrorData(loginParams);
        }
        return buildAuthToken(staff);
    }

    @Override
    public void addAdmin(Staff staff) {
        staff.setId(UUIDUtils.generateStaffUUID());
        Integer departmentId = staff.getDepartmentId();
        if (departmentId<1||departmentId>8){
            throw new BadRequestException("添加员工失败，不存在该部门").setErrorData(departmentId);
        }
        if (isExist(staff.getAccount())){
            throw new AccountAlreadyExistException("账号不可用，该管理员已存在").setErrorData(staff);
        }
        staffRepository.save(staff);
    }

    @Override
    public StaffVo getStaffProfile() {
        Authentication authentication = AuthContextHolder.getAuthContext().getAuthentication();
        Assert.notNull(authentication,"授权信息不能为空");
        Staff staff = authentication.getStaff();
        if (staff == null){
            throw new UnAuthorizedException("未授权请先登录");
        }
        String id = staff.getId();
        return findStaffVoByStaffId(id);
    }

    @Override
    public Staff findStaffById(String id) {
        Optional<Staff> staffOptional = staffRepository.findById(id);
        if (!staffOptional.isPresent()){
            throw new InternalServerErrorException("员工不存开或已被开除").setErrorData(id);
        }
        return staffOptional.get();
    }

    @Override
    public StaffPreviewVo findAllStaffPreview() {
        return new StaffPreviewVo(staffRepository.findAllStaffPreviewDto());
    }

    @Override
    public void adminLogout() {
        Authentication authentication = AuthContextHolder.getAuthContext().getAuthentication();
        Assert.notNull(authentication,"授权信息不为空");
        Staff staff = authentication.getStaff();
        String token = authentication.getToken();
        if (token == null){
            throw new UnAuthorizedException("未登录，不能注销");
        }
        redisTemplate.delete(token);
        log.info("员工"+staff.getName()+"注销");
    }

    @Override
    public void deleteStaffById(String id) {
        staffRepository.deleteById(id);
    }

    @Override
    public StaffPreviewVo findStaffPreviewByDepartmentId(Integer departmentId) {
        return new StaffPreviewVo(staffRepository.findStaffPreviewDtoByDepartmentId(departmentId));
    }

    @Override
    public List<StaffVo> findAllStaffVo() {
        return staffRepository.findAllStaffVo();
    }

    /**
     * 生成验证令牌
     * @param staff
     * @return
     */
    private AuthToken buildAuthToken(Staff staff){
        String token = UUIDUtils.generateToken();
        Long timeOut = kindlessProperties.getTokenExpiredIn();
        redisTemplate.opsForValue().set(token,staff,timeOut, TimeUnit.SECONDS);
        return new AuthToken(token,timeOut,staff.getId());
    }

    /**
     * 判断管理员是否存在
     * @param findAble
     * @return
     */
    private boolean isExist(String findAble){
        Staff staff = staffRepository.findByAccount(findAble);
        Optional<Staff> staffOptional = staffRepository.findById(findAble);
        return staffOptional.isPresent()||staff != null;
    }

    /**
     * 根据id查询员工信息
     * @param staffId 员工id
     * @return 员工信息
     */
    private StaffVo findStaffVoByStaffId(@NonNull String staffId){
        return staffRepository.findStaffVoByStaffId(staffId);
    }
}
