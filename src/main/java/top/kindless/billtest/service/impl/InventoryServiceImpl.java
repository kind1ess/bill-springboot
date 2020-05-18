package top.kindless.billtest.service.impl;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import top.kindless.billtest.exception.InternalServerErrorException;
import top.kindless.billtest.model.common.CommonGoodsInfo;
import top.kindless.billtest.model.entity.Commodity;
import top.kindless.billtest.model.entity.Inventory;
import top.kindless.billtest.model.entity.Specification;
import top.kindless.billtest.repository.InventoryRepository;
import top.kindless.billtest.service.CommodityService;
import top.kindless.billtest.service.InventoryService;
import top.kindless.billtest.service.SpecificationService;

import java.util.Optional;

@Service
@CacheConfig(cacheNames = "inventory")
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final CommodityService commodityService;
    private final SpecificationService specificationService;

    public InventoryServiceImpl(InventoryRepository inventoryRepository,
                                CommodityService commodityService,
                                SpecificationService specificationService) {
        this.inventoryRepository = inventoryRepository;
        this.commodityService = commodityService;
        this.specificationService = specificationService;
    }

    @Override
    @Cacheable(key = "#id")
    public Inventory findById(Integer id) {
        Optional<Inventory> inventoryOptional = inventoryRepository.findById(id);
        if (!inventoryOptional.isPresent()){
            throw new InternalServerErrorException("数据不见了┭┮﹏┭┮");
        }
        return inventoryOptional.get();
    }

    @Override
    public CommonGoodsInfo convertInventoryToCommonGoodsInfo(Inventory inventory) {
        Integer goodsId = inventory.getGoodsId();
        Integer commodityId = inventory.getCommodityId();
        Integer specificationId = inventory.getSpecificationId();
        Commodity commodity = commodityService.findById(commodityId);
        Specification specification = specificationService.findById(specificationId);
        String commodityName = commodity.getCommodityName();
        Float price = commodity.getPrice();
        String imgUrl = commodity.getImgUrl();
        String specificationName = specification.getSpecificationName();
        return new CommonGoodsInfo(goodsId,commodityId,commodityName,specificationId,specificationName,price,imgUrl);
    }

    @Override
    @CacheEvict(key = "#id")
    public void deleteInventoryById(Integer id) {
        inventoryRepository.deleteById(id);
    }

    @Override
    @CachePut(key = "#inventory.goodsId")
    public Inventory updateInventory(Inventory inventory) {
        inventoryRepository.saveAndFlush(inventory);
        return inventory;
    }

    @Override
    public void saveInventory(Inventory inventory) {
        inventoryRepository.save(inventory);
    }
}
