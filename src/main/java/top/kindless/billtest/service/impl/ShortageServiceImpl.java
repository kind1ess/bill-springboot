package top.kindless.billtest.service.impl;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import top.kindless.billtest.exception.InternalServerErrorException;
import top.kindless.billtest.model.common.AdminBillTitle;
import top.kindless.billtest.model.common.CommonListGoods;
import top.kindless.billtest.model.entity.BillShortage;
import top.kindless.billtest.model.entity.DetailShortage;
import top.kindless.billtest.model.params.ShortageParam;
import top.kindless.billtest.model.params.ShortageParams;
import top.kindless.billtest.model.vo.CommonBillPreviewVo;
import top.kindless.billtest.model.vo.ShortageVo;
import top.kindless.billtest.repository.BillShortageRepository;
import top.kindless.billtest.security.auth.Authentication;
import top.kindless.billtest.security.context.AuthContextHolder;
import top.kindless.billtest.service.DetailShortageService;
import top.kindless.billtest.service.ShortageService;
import top.kindless.billtest.utils.BillNoUtils;
import top.kindless.billtest.utils.StatusConst;

import java.util.List;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = "billShortage")
public class ShortageServiceImpl implements ShortageService {

    private final BillShortageRepository billShortageRepository;
    private final DetailShortageService detailShortageService;

    public ShortageServiceImpl(BillShortageRepository billShortageRepository,
                               DetailShortageService detailShortageService) {
        this.billShortageRepository = billShortageRepository;
        this.detailShortageService = detailShortageService;
    }

    @Override
    @Cacheable(key = "#id")
    public BillShortage findBillById(String id) {
        Optional<BillShortage> billShortageOptional = billShortageRepository.findById(id);
        if (!billShortageOptional.isPresent()){
            throw new InternalServerErrorException("缺货单表头单据不存在或已被删除");
        }
        return billShortageOptional.get();
    }

    @Override
    public List<BillShortage> findBillsByStatusId(Integer statusId) {
        return billShortageRepository.findAllByStatusId(statusId);
    }

    @Override
    public void deleteBillAndDetailBillsByBillId(String billId) {
//        Authentication authentication = AuthContextHolder.getAuthContext().getAuthentication();
//        if (authentication == null) {
//            throw new UnAuthorizedException("未授权请先登录");
//        }
//        billShortageRepository.deleteById(billId);
    }

    @Override
    public void updateBill(BillShortage billShortage, DetailShortage detailShortage) {
        updateBillShortage(billShortage);
        detailShortageService.updateDetailShortage(detailShortage);
    }

    @Override
    public void saveBillShortage(BillShortage billShortage) {
        billShortageRepository.save(billShortage);
    }

    @Override
    @CachePut(key = "#billShortage.id")
    public BillShortage updateBillShortage(BillShortage billShortage) {
        return billShortageRepository.saveAndFlush(billShortage);
    }

    @Override
    @CacheEvict(key = "#id")
    public void deleteBillShortageById(String id) {
        billShortageRepository.deleteById(id);
    }

    @Override
    public List<BillShortage> findAllBill() {
        return billShortageRepository.findAll();
    }

    @Override
    public String generateBillShortage(ShortageParams shortageParams) {
        Authentication authentication = AuthContextHolder.getAuthContext().getAuthentication();
        Assert.notNull(authentication,"验证信息不为空");
        String staffId = authentication.getStaff().getId();
        List<ShortageParam> params = shortageParams.getShortageParams();
        return generateAndSaveShortage(staffId,params);
    }

    @Override
    public ShortageVo findShortageVoById(String id) {
        List<CommonListGoods> listGoods = detailShortageService.findListGoodsByBillId(id);
        AdminBillTitle billTitle = billShortageRepository.findShortageDtoById(id);
        return new ShortageVo(billTitle,listGoods);
    }

    @Override
    public CommonBillPreviewVo findAllShortagePreviewVo() {
        return new CommonBillPreviewVo(billShortageRepository.findAllShortagePreviewVo());
    }

    @Override
    public CommonBillPreviewVo findAllShortagePreviewVo(Integer page, Integer size) {
        return new CommonBillPreviewVo(billShortageRepository.findAllShortagePreviewVo(PageRequest.of(page,size)));
    }

    @Override
    public CommonBillPreviewVo findAllShortagePreviewVo(Integer statusId) {
        return new CommonBillPreviewVo(billShortageRepository.findAllShortagePreviewVo(statusId));
    }

    @Override
    public CommonBillPreviewVo findAllShortagePreviewVo(Integer statusId, Integer page, Integer size) {
        return new CommonBillPreviewVo(billShortageRepository.findAllShortagePreviewVo(statusId,PageRequest.of(page,size)));
    }

    @Override
    public Long getCount() {
        return billShortageRepository.count();
    }

    @Override
    public Long getCount(Integer statusId) {
        return billShortageRepository.countAllByStatusId(statusId);
    }

    @Override
    public void setBillStatus(String billId, Integer statusId) {
        BillShortage billShortage = findBillById(billId);
        billShortage.setStatusId(statusId);
        saveBillShortage(billShortage);
    }

    private String generateAndSaveShortage(String staffId, List<ShortageParam> params){
        BillShortage billShortage = new BillShortage();
        String billId = BillNoUtils.generateBillId("SHT");
        billShortage.setId(billId);
        billShortage.setStaffId(staffId);
        billShortage.setStatusId(StatusConst.TO_BE_PURCHASE);
        saveBillShortage(billShortage);
        detailShortageService.generateAndSaveDetailShortage(billId,params);
        return billId;
    }
}
