package top.kindless.billtest.service.impl;

import org.springframework.stereotype.Service;
import top.kindless.billtest.exception.InternalServerErrorException;
import top.kindless.billtest.model.common.ListGoodsWithoutPrice;
import top.kindless.billtest.model.common.FlowReserveAmount;
import top.kindless.billtest.model.entity.DetailCheck;
import top.kindless.billtest.model.entity.DetailEnter;
import top.kindless.billtest.repository.DetailEnterRepository;
import top.kindless.billtest.service.CheckService;
import top.kindless.billtest.service.DetailCheckService;
import top.kindless.billtest.service.DetailEnterService;
import top.kindless.billtest.utils.StatusConst;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DetailEnterServiceImpl implements DetailEnterService {

    private final DetailEnterRepository detailEnterRepository;
    private final DetailCheckService detailCheckService;
    private final CheckService checkService;

    public DetailEnterServiceImpl(DetailEnterRepository detailEnterRepository,
                                  DetailCheckService detailCheckService,
                                  CheckService checkService) {
        this.detailEnterRepository = detailEnterRepository;
        this.detailCheckService = detailCheckService;
        this.checkService = checkService;
    }

    @Override
    public void saveDetailEnter(DetailEnter detailEnter) {
        detailEnterRepository.save(detailEnter);
    }

    @Override
    public void saveDetailEnterList(List<DetailEnter> detailEnterList) {
        detailEnterRepository.saveAll(detailEnterList);
    }

    @Override
    public DetailEnter updateDetailEnter(DetailEnter detailEnter) {
        return detailEnterRepository.saveAndFlush(detailEnter);
}

    @Override
    public void deleteDetailEnterById(Integer id) {
        detailEnterRepository.deleteById(id);
    }

    @Override
    public DetailEnter findDetailEnterById(Integer id) {
        Optional<DetailEnter> detailEnterOptional = detailEnterRepository.findById(id);
        if (!detailEnterOptional.isPresent()){
            throw new InternalServerErrorException("入库单明细数据不存在或已被删除").setErrorData(id);
        }
        return detailEnterOptional.get();
    }

    @Override
    public void generateAndSaveDetailEnter(String billId, List<String> checkIdList) {
        List<DetailEnter> detailEnters = new ArrayList<>();
        for (String checkId : checkIdList) {
            List<DetailCheck> detailCheckList = detailCheckService.findAllByBillId(checkId);
            List<DetailEnter> detailEnterList = detailCheckList.stream()
                    .map(detailCheck -> {
                        Integer goodsId = detailCheck.getGoodsId();
                        Integer actualAmount = detailCheck.getActualAmount();
                        DetailEnter detailEnter = new DetailEnter();
                        detailEnter.setAmount(actualAmount);
                        detailEnter.setBillId(billId);
                        detailEnter.setCheckId(checkId);
                        detailEnter.setGoodsId(goodsId);
                        return detailEnter;
                    }).collect(Collectors.toList());
            detailEnters.addAll(detailEnterList);
            checkService.setBillStatus(checkId,StatusConst.ENTERED);
        }
        saveDetailEnterList(detailEnters);
    }

    @Override
    public List<ListGoodsWithoutPrice> findListGoodsByBillId(String billId) {
        return detailEnterRepository.findListGoodsByBillId(billId);
    }

    @Override
    public List<FlowReserveAmount> countEnterAmount() {
        return detailEnterRepository.findAllSumAmount();
    }
}
