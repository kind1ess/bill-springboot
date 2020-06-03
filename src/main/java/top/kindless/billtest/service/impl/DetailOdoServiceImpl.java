package top.kindless.billtest.service.impl;

import org.springframework.stereotype.Service;
import top.kindless.billtest.model.common.ListGoodsWithoutPrice;
import top.kindless.billtest.model.common.FlowReserveAmount;
import top.kindless.billtest.model.entity.DetailOdo;
import top.kindless.billtest.repository.DetailOdoRepository;
import top.kindless.billtest.service.DetailOdoService;

import java.util.List;

@Service
public class DetailOdoServiceImpl implements DetailOdoService {

    private final DetailOdoRepository detailOdoRepository;

    public DetailOdoServiceImpl(DetailOdoRepository detailOdoRepository) {
        this.detailOdoRepository = detailOdoRepository;
    }

    @Override
    public void saveDetailOdo(DetailOdo detailOdo) {
        detailOdoRepository.save(detailOdo);
    }

    @Override
    public List<DetailOdo> findAllDetailOdoByOdoId(String odoId) {
        return detailOdoRepository.findAllByBillId(odoId);
    }

    @Override
    public List<ListGoodsWithoutPrice> findAllListGoodsByBillId(String billId) {
        return detailOdoRepository.findAllListGoodsByBillId(billId);
    }

    @Override
    public List<FlowReserveAmount> countOdoAmount() {
        return detailOdoRepository.findAllSumAmount();
    }
}
