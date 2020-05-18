package top.kindless.billtest.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import top.kindless.billtest.exception.InternalServerErrorException;
import top.kindless.billtest.model.entity.Commodity;
import top.kindless.billtest.repository.CommodityRepository;
import top.kindless.billtest.service.CommodityService;

import java.util.Optional;

@Service
@CacheConfig(cacheNames = "commodity")
public class CommodityServiceImpl implements CommodityService {

    private final CommodityRepository commodityRepository;

    public CommodityServiceImpl(CommodityRepository commodityRepository) {
        this.commodityRepository = commodityRepository;
    }

    @Override
    @Cacheable(key = "#id")
    public Commodity findById(Integer id) {
        Optional<Commodity> commodityOptional = commodityRepository.findById(id);
        if (!commodityOptional.isPresent()){
            throw new InternalServerErrorException("数据不见了┭┮﹏┭┮");
        }
        return commodityOptional.get();
    }

    @Override
    public void saveCommodity(Commodity commodity) {
        commodityRepository.save(commodity);
    }

    @Override
    @CachePut(key = "#commodity.id")
    public Commodity updateCommodity(Commodity commodity) {
        commodityRepository.saveAndFlush(commodity);
        return commodity;
    }

    @Override
    @CacheEvict(key = "#id")
    public void deleteCommodityById(Integer id) {
        commodityRepository.deleteById(id);
    }
}
