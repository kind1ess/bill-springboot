package top.kindless.billtest.service.impl;

import org.springframework.stereotype.Service;
import top.kindless.billtest.exception.InternalServerErrorException;
import top.kindless.billtest.model.entity.Commodity;
import top.kindless.billtest.repository.CommodityRepository;
import top.kindless.billtest.service.CommodityService;

import java.util.Optional;

@Service
public class CommodityServiceImpl implements CommodityService {

    private final CommodityRepository commodityRepository;

    public CommodityServiceImpl(CommodityRepository commodityRepository) {
        this.commodityRepository = commodityRepository;
    }

    @Override
    public Commodity findById(Integer id) {
        Optional<Commodity> commodityOptional = commodityRepository.findById(id);
        if (!commodityOptional.isPresent()){
            throw new InternalServerErrorException("数据不见了┭┮﹏┭┮");
        }
        return commodityOptional.get();
    }
}
