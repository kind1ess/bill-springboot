package top.kindless.billtest.service.impl;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import top.kindless.billtest.repository.StatusRepository;
import top.kindless.billtest.service.StatusService;

@Service
@CacheConfig(cacheNames = "status")
public class StatusServiceImpl implements StatusService {

    private final StatusRepository statusRepository;

    public StatusServiceImpl(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    @Override
    @Cacheable(key = "#id")
    public String findStatusNameById(Integer id) {
        return statusRepository.findStatusNameById(id);
    }
}
