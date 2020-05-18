package top.kindless.billtest.service;

import org.springframework.lang.NonNull;
import top.kindless.billtest.model.entity.Staff;
import top.kindless.billtest.model.params.LoginParams;
import top.kindless.billtest.security.token.AuthToken;

public interface AdminService {

    /**
     * 管理员登录
     * @param loginParams
     * @return
     */
    @NonNull
    AuthToken adminLogin(@NonNull LoginParams loginParams);

    /**
     * 添加管理员
     * @param staff
     */
    void addAdmin(@NonNull Staff staff);
}
