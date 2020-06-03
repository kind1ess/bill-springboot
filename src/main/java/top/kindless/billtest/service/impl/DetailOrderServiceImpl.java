package top.kindless.billtest.service.impl;

import org.springframework.stereotype.Service;
import top.kindless.billtest.exception.InternalServerErrorException;
import top.kindless.billtest.model.common.ListGoods;
import top.kindless.billtest.model.entity.DetailOrder;
import top.kindless.billtest.repository.DetailOrderRepository;
import top.kindless.billtest.service.DetailOrderService;

import java.util.List;
import java.util.Optional;

@Service
public class DetailOrderServiceImpl implements DetailOrderService {

    private final DetailOrderRepository detailOrderRepository;

    public DetailOrderServiceImpl(DetailOrderRepository detailOrderRepository) {
        this.detailOrderRepository = detailOrderRepository;
    }

    @Override
    public void saveDetailOrder(DetailOrder detailOrder) {
        detailOrderRepository.save(detailOrder);
    }

    @Override
    public void saveDetailOrderList(List<DetailOrder> detailOrderList) {
        detailOrderRepository.saveAll(detailOrderList);
    }

    @Override
    public DetailOrder findDetailOrderById(Integer id) {
        Optional<DetailOrder> detailOrderOptional = detailOrderRepository.findById(id);
        if (!detailOrderOptional.isPresent()){
            throw new InternalServerErrorException("订单明细数据不存在或已被删除").setErrorData(id);
        }
        return detailOrderOptional.get();
    }

    @Override
    public DetailOrder updateDetailOrder(DetailOrder detailOrder) {
        return detailOrderRepository.saveAndFlush(detailOrder);
    }

    @Override
    public void deleteDetailOrderById(Integer id) {
        detailOrderRepository.deleteById(id);
    }

    @Override
    public List<DetailOrder> findDetailOrderListByBillId(String billId) {
        return detailOrderRepository.findAllByBillId(billId);
    }

    @Override
    public void deleteAllDetailOrdersByBillId(String billId) {
        detailOrderRepository.deleteAllByBillId(billId);
    }

    @Override
    public List<ListGoods> findAllListGoodsByBillId(String billId) {
        return detailOrderRepository.findAllListGoodsByBillId(billId);
    }
}
