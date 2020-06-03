package top.kindless.billtest.service.impl;

import org.springframework.stereotype.Service;
import top.kindless.billtest.model.common.AllReserveAmount;
import top.kindless.billtest.model.common.CommonGoodsInfo;
import top.kindless.billtest.model.common.FlowReserveAmount;
import top.kindless.billtest.model.entity.Inventory;
import top.kindless.billtest.service.DetailEnterService;
import top.kindless.billtest.service.DetailOdoService;
import top.kindless.billtest.service.InventoryService;
import top.kindless.billtest.service.ReserveService;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReserveServiceImpl implements ReserveService {

    private final DetailOdoService detailOdoService;
    private final DetailEnterService detailEnterService;
    private final InventoryService inventoryService;

    public ReserveServiceImpl(DetailOdoService detailOdoService,
                              DetailEnterService detailEnterService,
                              InventoryService inventoryService) {
        this.detailOdoService = detailOdoService;
        this.detailEnterService = detailEnterService;
        this.inventoryService = inventoryService;
    }

    @Override
    public List<AllReserveAmount> countAllAmount() {
        List<FlowReserveAmount> odoReserveAmountList = detailOdoService.countOdoAmount();
        List<FlowReserveAmount> enterReserveAmountList = detailEnterService.countEnterAmount();
        Set<Integer> set = new HashSet<>();
        odoReserveAmountList.forEach(item -> set.add(item.getGoodsId()));
        enterReserveAmountList.forEach(item -> set.add(item.getGoodsId()));
        Map<Integer,Long> odoMap = new HashMap<>();
        Map<Integer,Long> enterMap = new HashMap<>();
        odoReserveAmountList.forEach(flowReserveAmount -> {
            odoMap.put(flowReserveAmount.getGoodsId(),flowReserveAmount.getSumAmount());
        });
        enterReserveAmountList.forEach(flowReserveAmount -> {
            enterMap.put(flowReserveAmount.getGoodsId(),flowReserveAmount.getSumAmount());
        });
        return set.stream()
                .map(goodsId -> {
                    Inventory inventory = inventoryService.findById(goodsId);
                    CommonGoodsInfo commonGoodsInfo = inventoryService.convertInventoryToCommonGoodsInfo(inventory);
                    AllReserveAmount allReserveAmount = new AllReserveAmount();
                    allReserveAmount.setGoodsId(goodsId);
                    Long odoAmount = odoMap.get(goodsId);
                    Long enterAmount = enterMap.get(goodsId);
                    if (odoAmount == null) {
                        allReserveAmount.setOdoAmount(0L);
                    } else {
                        allReserveAmount.setOdoAmount(odoAmount);
                    }
                    if (enterAmount == null) {
                        allReserveAmount.setEnterAmount(0L);
                    } else {
                        allReserveAmount.setEnterAmount(enterAmount);
                    }
                    allReserveAmount.setAllAmount(allReserveAmount.getEnterAmount() - allReserveAmount.getOdoAmount());
                    allReserveAmount.setCommodityName(commonGoodsInfo.getCommodityName());
                    allReserveAmount.setSpecificationName(commonGoodsInfo.getSpecificationName());
                    return allReserveAmount;
                }).collect(Collectors.toList());
    }
}
