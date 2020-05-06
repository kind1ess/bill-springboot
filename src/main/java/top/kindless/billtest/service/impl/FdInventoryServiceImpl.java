package top.kindless.billtest.service.impl;

import org.springframework.stereotype.Service;
import top.kindless.billtest.repository.FdInventoryRepository;
import top.kindless.billtest.service.FdInventoryService;

@Service
public class FdInventoryServiceImpl implements FdInventoryService {

    private final FdInventoryRepository fdInventoryRepository;

    public FdInventoryServiceImpl(FdInventoryRepository fdInventoryRepository) {
        this.fdInventoryRepository = fdInventoryRepository;
    }

    @Override
    public Integer findAmountById(Integer id) {
        return fdInventoryRepository.findAmountById(id);
    }
}
