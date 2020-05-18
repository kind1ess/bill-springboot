package top.kindless.billtest.service;

import org.springframework.lang.NonNull;

public interface StatusService {

    /**
     * 根据状态id查询状态名称
     * @param id
     * @return
     */
    String findStatusNameById(@NonNull Integer id);

}
