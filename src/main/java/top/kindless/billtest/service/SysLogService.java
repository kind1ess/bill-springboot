package top.kindless.billtest.service;

import org.springframework.data.domain.Page;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import top.kindless.billtest.model.entity.SysLogEntity;

public interface SysLogService {

    @Transactional
    void save(@NonNull SysLogEntity sysLogEntity);

    Page<SysLogEntity> listAll(@NonNull Integer page,@NonNull Integer size);
}
