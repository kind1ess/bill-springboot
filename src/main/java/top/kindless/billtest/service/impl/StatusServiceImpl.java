package top.kindless.billtest.service.impl;

import org.springframework.stereotype.Service;
import top.kindless.billtest.repository.StatusRepository;
import top.kindless.billtest.service.StatusService;

@Service
public class StatusServiceImpl implements StatusService {

    private final StatusRepository statusRepository;

    public StatusServiceImpl(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    @Override
    public String findStatusNameById(Integer id) {
        return statusRepository.findStatusNameById(id);
    }
}
