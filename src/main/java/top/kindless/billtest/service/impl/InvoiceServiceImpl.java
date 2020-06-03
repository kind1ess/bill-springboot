package top.kindless.billtest.service.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import top.kindless.billtest.exception.BadRequestException;
import top.kindless.billtest.model.common.CommonBillPreviewWithUpdateTime;
import top.kindless.billtest.model.common.CommonListGoods;
import top.kindless.billtest.model.dto.InvoiceDto;
import top.kindless.billtest.model.entity.*;
import top.kindless.billtest.model.params.BillParams;
import top.kindless.billtest.model.vo.CommonBillPreviewVo;
import top.kindless.billtest.model.vo.InvoiceVo;
import top.kindless.billtest.repository.BillInvoiceRepository;
import top.kindless.billtest.security.auth.Authentication;
import top.kindless.billtest.security.context.AuthContextHolder;
import top.kindless.billtest.service.*;
import top.kindless.billtest.utils.BillId;
import top.kindless.billtest.utils.BillNoUtils;
import top.kindless.billtest.utils.StatusConst;
import top.kindless.billtest.utils.UUIDUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final BillInvoiceRepository billInvoiceRepository;
    private final DetailInvoiceService detailInvoiceService;
    private final DetailOdoService detailOdoService;
    private final OdoService odoService;
    private final OrderService orderService;

    public InvoiceServiceImpl(BillInvoiceRepository billInvoiceRepository,
                              DetailInvoiceService detailInvoiceService,
                              DetailOdoService detailOdoService,
                              OdoService odoService,
                              OrderService orderService) {
        this.billInvoiceRepository = billInvoiceRepository;
        this.detailInvoiceService = detailInvoiceService;
        this.detailOdoService = detailOdoService;
        this.odoService = odoService;
        this.orderService = orderService;
    }


    @Override
    public CommonBillPreviewVo generateInvoiceList(BillParams billParams) {
        Authentication authentication = AuthContextHolder.getAuthContext().getAuthentication();
        Assert.notNull(authentication, "授权信息不能为空");
        Staff staff = authentication.getStaff();
        String staffId = staff.getId();
        List<String> billIdList = billParams.getBillIdList();
        List<CommonBillPreviewWithUpdateTime> billPreviewList = new ArrayList<>();
        //获取出库单集合,默认不允许存在一张订货单对应多张出库单的情况，并且每张订货单的货物都全部出完
        for (String odoId : billIdList) {
            BillOdo billOdo = odoService.findBillById(odoId);
            if (!billOdo.getStatusId().equals(StatusConst.TO_BE_SHIPPED)) {
                throw new BadRequestException("不能将待发货状态以外的出库单发货").setErrorData(odoId);
            }
            List<DetailOdo> allDetailOdoByOdoId = detailOdoService.findAllDetailOdoByOdoId(odoId);
            Set<String> orderIdSet = new HashSet<>();
            //遍历每一个出库单的明细，属于同一张订货单的明细加入同一张发货单
            for (DetailOdo detailOdo : allDetailOdoByOdoId) {
                String orderId = detailOdo.getOrderId();
                orderIdSet.add(orderId);
            }
            CommonBillPreviewWithUpdateTime commonBillPreviewWithUpdateTime;
            //对于每一张订货单生成一张发货单
            for (String orderId : orderIdSet) {
                commonBillPreviewWithUpdateTime = generateAndSaveBillInvoice(staffId, odoId, orderId);
                String billId = commonBillPreviewWithUpdateTime.getBillId();
                //生成的不同的发货单都加入集合
                billPreviewList.add(commonBillPreviewWithUpdateTime);
                //生成发货单明细
                List<DetailInvoice> detailInvoiceList = allDetailOdoByOdoId
                        .stream()
                        .filter(s -> s.getOrderId().equals(orderId))
                        .map(s -> {
                            Integer goodsId = s.getGoodsId();
                            Integer amount = s.getAmount();
                            DetailInvoice detailInvoice = new DetailInvoice();
                            detailInvoice.setBillId(billId);
                            detailInvoice.setGoodsId(goodsId);
                            detailInvoice.setAmount(amount);
                            return detailInvoice;
                        }).collect(Collectors.toList());
                detailInvoiceService.saveAllDetailInvoice(detailInvoiceList);
                orderService.setBillStatus(orderId, StatusConst.SHIPPED);
            }
            odoService.setBillStatus(odoId, StatusConst.SHIPPED);
        }


        return new CommonBillPreviewVo(billPreviewList);
    }

    @Override
    public void saveBillInvoice(BillInvoice billInvoice) {
        billInvoiceRepository.save(billInvoice);
    }

    @Override
    public InvoiceVo findInvoiceVoById(String invoiceId) {
        InvoiceDto invoiceDto = findInvoiceDtoByInvoiceId(invoiceId);
        List<CommonListGoods> commonListGoodsList = detailInvoiceService.findAllListGoodsByBillId(invoiceId);
        return new InvoiceVo(invoiceDto,commonListGoodsList);
    }

    @Override
    public CommonBillPreviewVo findAllInvoicePreviewVo() {
        List<CommonBillPreviewWithUpdateTime> commonBillPreviewWithUpdateTimeList = findAllInvoicePreview();
        return new CommonBillPreviewVo(commonBillPreviewWithUpdateTimeList);
    }

    @Override
    public CommonBillPreviewVo findAllInvoicePreviewVo(Integer page,Integer size) {
        return new CommonBillPreviewVo(billInvoiceRepository.findAllInvoicePreview(PageRequest.of(page,size)));
    }

    @Override
    public Long getCount() {
        return billInvoiceRepository.count();
    }

    @Override
    public Long getCount(Integer statusId) {
        return billInvoiceRepository.countAllByStatusId(statusId);
    }

    @Override
    public BillInvoice findBillById(String id) {
        Optional<BillInvoice> invoiceOptional = billInvoiceRepository.findById(id);
        if (!invoiceOptional.isPresent()){
            throw new BadRequestException("不存在该订货单或已被删除").setErrorData(id);
        }
        return invoiceOptional.get();
    }

    @Override
    public List<BillInvoice> findBillsByStatusId(Integer statusId) {
        return null;
    }

    @Override
    public void deleteBillAndDetailBillsByBillId(String billId) {

    }

    @Override
    public void updateBill(BillInvoice billInvoice, DetailInvoice detailInvoice) {

    }

    @Override
    public List<BillInvoice> findAllBill() {
        return null;
    }

    /**
     * 保存发货单表头并且返回预览信息
     *
     * @param staffId 员工id
     * @param odoId   出库单id
     * @param orderId 订单id
     * @return 发货单预览信息
     */
    private CommonBillPreviewWithUpdateTime generateAndSaveBillInvoice(String staffId, String odoId, String orderId) {
        BillInvoice billInvoice = new BillInvoice();
        billInvoice.setId(BillNoUtils.generateBillId("INV"));
        billInvoice.setStaffId(staffId);
        billInvoice.setOdoId(odoId);
        billInvoice.setOrderId(orderId);
        billInvoice.setStatusId(StatusConst.SHIPPED);
//        BillInvoice billInvoice1 = saveBillInvoice(billInvoice);
        //先保存之后再查询，避免出现事务管理问题
        saveBillInvoice(billInvoice);
        return findInvoicePreviewByInvoiceId(billInvoice.getId());
    }

    /**
     * 查询所有发货单预览信息
     * @return 所有发货单预览信息
     */
    private List<CommonBillPreviewWithUpdateTime> findAllInvoicePreview(){
        return billInvoiceRepository.findAllInvoicePreview();
    }

    /**
     * 根据发货单id查询发货单预览信息
     * @param invoiceId 发货单id
     * @return 发货单预览信息
     */
    private CommonBillPreviewWithUpdateTime findInvoicePreviewByInvoiceId(String invoiceId){
        return billInvoiceRepository.findInvoicePreviewByBillId(invoiceId);
    }

    /**
     * 根据发货单id查询发货单表头
     * @param invoiceId 发货单id
     * @return 发货单表头
     */
    private InvoiceDto findInvoiceDtoByInvoiceId(String invoiceId){
        return billInvoiceRepository.findInvoiceDtoByBillId(invoiceId);
    }
}
