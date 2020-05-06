package top.kindless.billtest.service.impl;

import org.springframework.stereotype.Service;
import top.kindless.billtest.exception.InternalServerErrorException;
import top.kindless.billtest.model.entity.Inventory;
import top.kindless.billtest.repository.InventoryRepository;
import top.kindless.billtest.service.InventoryService;

import java.util.Optional;

@Service
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryServiceImpl(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public Inventory findById(Integer id) {
        Optional<Inventory> inventoryOptional = inventoryRepository.findById(id);
        if (!inventoryOptional.isPresent()){
            throw new InternalServerErrorException("数据不见了┭┮﹏┭┮");
        }
        return inventoryOptional.get();
    }
}
