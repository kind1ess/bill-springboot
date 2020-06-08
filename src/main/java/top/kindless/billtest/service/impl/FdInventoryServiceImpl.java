package top.kindless.billtest.service.impl;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import top.kindless.billtest.exception.AmountOutOfBoundException;
import top.kindless.billtest.exception.BadRequestException;
import top.kindless.billtest.exception.InternalServerErrorException;
import top.kindless.billtest.model.common.AllReserveAmount;
import top.kindless.billtest.model.common.CommonGoodsInfo;
import top.kindless.billtest.model.common.ListGoods;
import top.kindless.billtest.model.dto.GoodsDto;
import top.kindless.billtest.model.entity.FdInventory;
import top.kindless.billtest.model.entity.Inventory;
import top.kindless.billtest.model.vo.GoodsVo;
import top.kindless.billtest.repository.FdInventoryRepository;
import top.kindless.billtest.service.FdInventoryService;
import top.kindless.billtest.service.InventoryService;
import top.kindless.billtest.service.ReserveService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@CacheConfig(cacheNames = "fdInventory")
public class FdInventoryServiceImpl implements FdInventoryService {

    private final FdInventoryRepository fdInventoryRepository;

    public FdInventoryServiceImpl(FdInventoryRepository fdInventoryRepository) {
        this.fdInventoryRepository = fdInventoryRepository;
    }

    @Override
    public Integer findAmountById(Integer id) {
        return fdInventoryRepository.findAmountById(id);
    }


    @Override
    public GoodsVo findAllFdGoods() {
        List<GoodsDto> goodsDtoList = fdInventoryRepository.findAllGoodsDto();
        return new GoodsVo(goodsDtoList);
    }

    /**
     * 更新库存
     * @param fdInventory must not be null
     */
    @Override
    @CachePut(key = "#fdInventory.goodsId")
    public FdInventory updateFdInventory(@NonNull FdInventory fdInventory){
        fdInventoryRepository.saveAndFlush(fdInventory);
        return fdInventory;
    }

    @Override
    @Cacheable(key = "#id")
    public FdInventory findById(Integer id) {
        Optional<FdInventory> fdInventoryOptional = fdInventoryRepository.findById(id);
        if (!fdInventoryOptional.isPresent())
            throw new BadRequestException("不存在该商品或已被删除").setErrorData(id);
        return fdInventoryOptional.get();
    }

    @Override
    public FdInventory findByGoodsId(Integer goodsId) {
        return fdInventoryRepository.findByGoodsId(goodsId);
    }

    @Override
    public void saveFdInventory(FdInventory fdInventory) {
        fdInventoryRepository.save(fdInventory);
    }

    @Override
    @CacheEvict(key = "#id")
    public void deleteById(Integer id) {
        fdInventoryRepository.deleteById(id);
    }

}
