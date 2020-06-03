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
import top.kindless.billtest.model.common.CommonGoodsInfo;
import top.kindless.billtest.model.common.ListGoods;
import top.kindless.billtest.model.dto.GoodsDto;
import top.kindless.billtest.model.entity.FdInventory;
import top.kindless.billtest.model.entity.Inventory;
import top.kindless.billtest.model.vo.GoodsVo;
import top.kindless.billtest.repository.FdInventoryRepository;
import top.kindless.billtest.service.FdInventoryService;
import top.kindless.billtest.service.InventoryService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = "fdInventory")
public class FdInventoryServiceImpl implements FdInventoryService {

    private final FdInventoryRepository fdInventoryRepository;
    private final InventoryService inventoryService;

    public FdInventoryServiceImpl(FdInventoryRepository fdInventoryRepository,
                                  InventoryService inventoryService) {
        this.fdInventoryRepository = fdInventoryRepository;
        this.inventoryService = inventoryService;
    }

    @Override
    public Integer findAmountById(Integer id) {
        return fdInventoryRepository.findAmountById(id);
    }

    @Override
    public void reduceFdInventoryByOrderGoodsList(List<ListGoods> goodsList) {
        FdInventory fdInventory;
        for (ListGoods listGoods : goodsList) {
            Integer amount = listGoods.getAmount();
            Integer goodsId = listGoods.getGoodsId();
            fdInventory = findByGoodsId(goodsId);
            Integer curAmount = fdInventory.getCurAmount();
            if (amount>curAmount){
                throw new AmountOutOfBoundException("创建订单失败，库存不足").setErrorData(listGoods);
            }
            fdInventory.setCurAmount(curAmount-amount);
            updateFdInventory(fdInventory);
        }
    }

    @Override
    public GoodsVo findAllFdGoods() {
        List<FdInventory> fdInventoryList = fdInventoryRepository.findAll();
        if (fdInventoryList.isEmpty()){
            throw new InternalServerErrorException("仓库数据不存在或已被删除");
        }
        List<GoodsDto> goodsDtoList = new ArrayList<>();
        GoodsDto goodsDto;
        for (FdInventory fdInventory : fdInventoryList) {
            Integer goodsId = fdInventory.getGoodsId();
            Integer curAmount = fdInventory.getCurAmount();
            Inventory inventory = inventoryService.findById(goodsId);
            CommonGoodsInfo commonGoodsInfo = inventoryService.convertInventoryToCommonGoodsInfo(inventory);
            goodsDto = convertCommonGoodsInfoToGoodsDto(commonGoodsInfo,curAmount);
            goodsDtoList.add(goodsDto);
        }
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

    /**
     * 将CommonGoodsInfo转换为GoodsDto
     * @param commonGoodsInfo must not be null
     * @param goodsDtoAmount must not be null
     * @return goodsDto must not be null
     */
    @NonNull
    private GoodsDto convertCommonGoodsInfoToGoodsDto(@NonNull CommonGoodsInfo commonGoodsInfo,@NonNull Integer goodsDtoAmount){
        Integer goodsId = commonGoodsInfo.getGoodsId();
        Integer commodityId = commonGoodsInfo.getCommodityId();
        Integer specificationId = commonGoodsInfo.getSpecificationId();
        String commodityName = commonGoodsInfo.getCommodityName();
        String specificationName = commonGoodsInfo.getSpecificationName();
        Float price = commonGoodsInfo.getPrice();
        String imgUrl = commonGoodsInfo.getImgUrl();
        GoodsDto goodsDto = new GoodsDto();
        goodsDto.setGoodsId(goodsId);
        goodsDto.setCommodityId(commodityId);
        goodsDto.setSpecificationId(specificationId);
        goodsDto.setCommodityName(commodityName);
        goodsDto.setSpecificationName(specificationName);
        goodsDto.setPrice(price);
        goodsDto.setAmount(goodsDtoAmount);
        goodsDto.setImgUrl(imgUrl);
        return goodsDto;
    }
}
