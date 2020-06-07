package top.kindless.billtest.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import top.kindless.billtest.model.entity.SysLogEntity;
import top.kindless.billtest.repository.SysLogRepository;
import top.kindless.billtest.service.SysLogService;

@Service
public class SysLogServiceImpl implements SysLogService {

    private final SysLogRepository sysLogRepository;

    public SysLogServiceImpl(SysLogRepository sysLogRepository) {
        this.sysLogRepository = sysLogRepository;
    }

    @Override
    public void save(SysLogEntity sysLogEntity) {
        sysLogRepository.save(sysLogEntity);
    }

    @Override
    public Page<SysLogEntity> listAll(Integer page, Integer size) {
        return sysLogRepository.findAll(PageRequest.of(page,size, Sort.by("date").descending()));
    }
}
