package top.kindless.billtest.service.impl;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import top.kindless.billtest.exception.InternalServerErrorException;
import top.kindless.billtest.model.common.CommonBillPreviewWithUpdateTime;
import top.kindless.billtest.model.common.ListGoodsWithoutPrice;
import top.kindless.billtest.model.dto.EnterDto;
import top.kindless.billtest.model.entity.BillEnter;
import top.kindless.billtest.model.entity.DetailEnter;
import top.kindless.billtest.model.params.BillParams;
import top.kindless.billtest.model.vo.CommonBillPreviewVo;
import top.kindless.billtest.model.vo.EnterVo;
import top.kindless.billtest.repository.BillEnterRepository;
import top.kindless.billtest.security.auth.Authentication;
import top.kindless.billtest.security.context.AuthContextHolder;
import top.kindless.billtest.service.DetailEnterService;
import top.kindless.billtest.service.EnterService;
import top.kindless.billtest.utils.BillId;
import top.kindless.billtest.utils.BillNoUtils;
import top.kindless.billtest.utils.StatusConst;
import top.kindless.billtest.utils.UUIDUtils;

import java.util.List;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = "billEnter")
public class EnterServiceImpl implements EnterService {

    private final BillEnterRepository billEnterRepository;
    private final DetailEnterService detailEnterService;

    public EnterServiceImpl(BillEnterRepository billEnterRepository,
                            DetailEnterService detailEnterService) {
        this.billEnterRepository = billEnterRepository;
        this.detailEnterService = detailEnterService;
    }

    @Override
    @Cacheable(key = "#id")
    public BillEnter findBillById(String id) {
        Optional<BillEnter> billEnterOptional = billEnterRepository.findById(id);
        if (!billEnterOptional.isPresent()){
            throw new InternalServerErrorException("入库单表头数据不存在或已被删除");
        }
        return billEnterOptional.get();
    }

    @Override
    public List<BillEnter> findBillsByStatusId(Integer statusId) {
        return billEnterRepository.findAllByStatusId(statusId);
    }

    @Override
    public void deleteBillAndDetailBillsByBillId(String billId) {

    }

    @Override
    public void updateBill(BillEnter billEnter, DetailEnter detailEnter) {
        billEnterRepository.saveAndFlush(billEnter);
        detailEnterService.updateDetailEnter(detailEnter);
    }

    @Override
    public void saveBillEnter(BillEnter billEnter) {
        billEnterRepository.save(billEnter);
    }

    @Override
    public BillEnter updateBillEnter(BillEnter billEnter) {
        billEnterRepository.saveAndFlush(billEnter);
        return billEnter;
    }

    @Override
    public void deleteBillEnterById(String id) {
        billEnterRepository.deleteById(id);
    }

    @Override
    public List<BillEnter> findAllBill() {
        return billEnterRepository.findAll();
    }

    @Override
    public String generateEnter(BillParams billParams) {
        Authentication authentication = AuthContextHolder.getAuthContext().getAuthentication();
        Assert.notNull(authentication,"授权信息不能为空");
        String staffId = authentication.getStaff().getId();
        List<String> billIdList = billParams.getBillIdList();
        return generateAndSaveBillEnter(staffId,billIdList);
    }

    @Override
    public EnterVo findEnterVoById(String id) {
        EnterDto enterDto = billEnterRepository.findEnterDtoById(id);
        List<ListGoodsWithoutPrice> listGoods = detailEnterService.findListGoodsByBillId(id);
        return new EnterVo(enterDto,listGoods);
    }

    @Override
    public CommonBillPreviewVo findAllEnterPreview() {
        List<CommonBillPreviewWithUpdateTime> previewList = billEnterRepository.findAllEnterPreview();
        return new CommonBillPreviewVo(previewList);
    }

    @Override
    public CommonBillPreviewVo findAllEnterPreview(Integer statusId) {
        return null;
    }

    @Override
    public CommonBillPreviewVo findAllEnterPreviewPageBy(Integer page, Integer size) {
        return new CommonBillPreviewVo(billEnterRepository.findAllEnterPreview(PageRequest.of(page,size)));
    }

    @Override
    public Long getCount() {
        return billEnterRepository.count();
    }

    private String generateAndSaveBillEnter(String staffId, List<String> billIdList){
        BillEnter billEnter = new BillEnter();
        String enterUUID = BillNoUtils.generateBillId("ENT");
        billEnter.setId(enterUUID);
        billEnter.setStaffId(staffId);
        billEnter.setStatusId(StatusConst.ENTERED);
        saveBillEnter(billEnter);
        detailEnterService.generateAndSaveDetailEnter(enterUUID,billIdList);
        return enterUUID;
    }
}
