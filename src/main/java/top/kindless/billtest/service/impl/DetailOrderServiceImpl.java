package top.kindless.billtest.service.impl;

import org.springframework.stereotype.Service;
import top.kindless.billtest.exception.InternalServerErrorException;
import top.kindless.billtest.exception.UnAuthorizedException;
import top.kindless.billtest.model.entity.DetailOrder;
import top.kindless.billtest.repository.DetailOrderRepository;
import top.kindless.billtest.security.auth.Authentication;
import top.kindless.billtest.security.context.AuthContextHolder;
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
        Authentication authentication = AuthContextHolder.getAuthContext().getAuthentication();
        if (authentication == null){
            throw new UnAuthorizedException("未授权请先登录");
        }
        detailOrderRepository.save(detailOrder);
    }

    @Override
    public void saveDetailOrderList(List<DetailOrder> detailOrderList) {
        for (DetailOrder detailOrder : detailOrderList) {
            saveDetailOrder(detailOrder);
        }
    }

    @Override
    public DetailOrder findDetailOrderById(Integer id) {
        Authentication authentication = AuthContextHolder.getAuthContext().getAuthentication();
        if (authentication == null){
            throw new UnAuthorizedException("未授权请先登录");
        }
        Optional<DetailOrder> detailOrderOptional = detailOrderRepository.findById(id);
        if (!detailOrderOptional.isPresent()){
            throw new InternalServerErrorException("订单明细数据不存在或已被删除").setErrorData(id);
        }
        return detailOrderOptional.get();
    }

    @Override
    public DetailOrder updateDetailOrder(DetailOrder detailOrder) {
        Authentication authentication = AuthContextHolder.getAuthContext().getAuthentication();
        if (authentication == null){
            throw new UnAuthorizedException("未授权请先登录");
        }
        return detailOrderRepository.saveAndFlush(detailOrder);
    }

    @Override
    public void deleteDetailOrderById(Integer id) {
        Authentication authentication = AuthContextHolder.getAuthContext().getAuthentication();
        if (authentication == null){
            throw new UnAuthorizedException("未授权请先登录");
        }
        detailOrderRepository.deleteById(id);
    }

    @Override
    public List<DetailOrder> findDetailOrderListByBillId(String billId) {
        Authentication authentication = AuthContextHolder.getAuthContext().getAuthentication();
        if (authentication == null){
            throw new UnAuthorizedException("未授权请先登录");
        }
        return detailOrderRepository.findAllByBillId(billId);
    }

    @Override
    public void deleteAllDetailOrdersByBillId(String billId) {
        Authentication authentication = AuthContextHolder.getAuthContext().getAuthentication();
        if (authentication == null){
            throw new UnAuthorizedException("未授权请先登录");
        }
        detailOrderRepository.deleteAllByBillId(billId);
    }
}
