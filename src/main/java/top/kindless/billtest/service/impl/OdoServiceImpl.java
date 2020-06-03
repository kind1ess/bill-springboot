package top.kindless.billtest.service.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import top.kindless.billtest.exception.BadRequestException;
import top.kindless.billtest.exception.InternalServerErrorException;
import top.kindless.billtest.model.common.CommonBillPreviewWithUpdateTime;
import top.kindless.billtest.model.common.ListGoods;
import top.kindless.billtest.model.common.ListGoodsWithoutPrice;
import top.kindless.billtest.model.dto.OdoDto;
import top.kindless.billtest.model.dto.OrderDto;
import top.kindless.billtest.model.entity.*;
import top.kindless.billtest.model.params.BillParams;
import top.kindless.billtest.model.vo.CommonBillPreviewVo;
import top.kindless.billtest.model.vo.OdoVo;
import top.kindless.billtest.model.vo.OrderVo;
import top.kindless.billtest.repository.BillOdoRepository;
import top.kindless.billtest.security.auth.Authentication;
import top.kindless.billtest.security.context.AuthContextHolder;
import top.kindless.billtest.service.*;
import top.kindless.billtest.utils.BillId;
import top.kindless.billtest.utils.BillNoUtils;
import top.kindless.billtest.utils.StatusConst;
import top.kindless.billtest.utils.UUIDUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OdoServiceImpl implements OdoService {

    private final BillOdoRepository billOdoRepository;
    private final DetailOdoService detailOdoService;
    private final OrderService orderService;

    public OdoServiceImpl(BillOdoRepository billOdoRepository,
                          DetailOdoService detailOdoService,
                          OrderService orderService) {
        this.billOdoRepository = billOdoRepository;
        this.detailOdoService = detailOdoService;
        this.orderService = orderService;
    }


    @Override
    public String addToOdo(BillParams billParams) {
        Authentication authentication = AuthContextHolder.getAuthContext().getAuthentication();
        Assert.notNull(authentication,"授权信息不能为空");
        String staffId = authentication.getStaff().getId();
        List<String> orderIdList = billParams.getBillIdList();
        List<OrderVo> orderVoList = new ArrayList<>();
        for (String orderId : orderIdList) {
            OrderVo orderVo = orderService.findOrderVoByBillId(orderId);
            orderVoList.add(orderVo);
//            orderService.setBillStatus(orderId,StatusConst.TO_BE_SHIPPED);
        }
        return generateAndSaveOdo(staffId,orderVoList);
    }

    @Override
    public void saveBillOdo(BillOdo billOdo) {
        billOdoRepository.save(billOdo);
    }

    @Override
    public CommonBillPreviewVo findAllOdoPreviewVo() {
        return new CommonBillPreviewVo(billOdoRepository.findAllOdoPreview());
    }

    @Override
    public CommonBillPreviewVo findAllOdoPreviewVo(Integer page, Integer size) {
        return new CommonBillPreviewVo(billOdoRepository.findAllOdoPreview(PageRequest.of(page,size)));
    }

    @Override
    public OdoVo findOdoVoById(String odoId) {
        OdoDto odoDto = billOdoRepository.findOdoDtoByBillId(odoId);
        List<ListGoodsWithoutPrice> goodsWithoutPriceList = detailOdoService.findAllListGoodsByBillId(odoId);
        return new OdoVo(odoDto,goodsWithoutPriceList);
    }

    @Override
    public CommonBillPreviewVo findOdoPreviewVoByStatusId(Integer statusId) {
        return new CommonBillPreviewVo(billOdoRepository.findOdoPreviewByStatusId(statusId));
    }

    @Override
    public CommonBillPreviewVo findOdoPreviewVoByStatusId(Integer statusId, Integer page, Integer size) {
        return new CommonBillPreviewVo(billOdoRepository.findOdoPreviewByStatusId(statusId,PageRequest.of(page,size)));
    }

    @Override
    public Long getCount() {
        return billOdoRepository.count();
    }

    @Override
    public Long getCount(Integer statusId) {
        return billOdoRepository.countAllByStatusId(statusId);
    }

    @Override
    public BillOdo findBillById(String id) {
        Optional<BillOdo> billOdoOptional = billOdoRepository.findById(id);
        if (!billOdoOptional.isPresent()){
            throw new InternalServerErrorException("出库单不存在或已被删除").setErrorData(id);
        }
        return billOdoOptional.get();
    }

    @Override
    public List<BillOdo> findBillsByStatusId(Integer statusId) {
        return null;
    }

    @Override
    public void deleteBillAndDetailBillsByBillId(String billId) {

    }

    @Override
    public void updateBill(BillOdo billOdo, DetailOdo detailOdo) {

    }

    @Override
    public List<BillOdo> findAllBill() {
        return null;
    }

    @Override
    public void setBillStatus(String billId, Integer statusId) {
        BillOdo billOdo = findBillById(billId);
        if (!statusId.equals(StatusConst.SHIPPED)){
            throw new BadRequestException("设置状态错误").setErrorData(statusId);
        }
        billOdo.setStatusId(statusId);
        saveBillOdo(billOdo);
    }

    /**
     * 生成并保存出库单表头及明细
     * @param staffId 员工id
     * @param orderVoList 订单vo集合
     * @return 出库单id
     */
    private String generateAndSaveOdo(String staffId,List<OrderVo> orderVoList){
        String odoId = generateAndSaveBillOdo(staffId);
        generateAndSaveDetailOdo(odoId,orderVoList);
        return odoId;
    }

    /**
     * 生成并保存出库单表头信息
     * @param staffId 员工id
     * @return 出库单id
     */
    private String generateAndSaveBillOdo(String staffId){
        BillOdo billOdo = new BillOdo();
        String odoId = BillNoUtils.generateBillId("ODO");
        billOdo.setId(odoId);
        billOdo.setId(odoId);
        billOdo.setStaffId(staffId);
        billOdo.setStatusId(StatusConst.TO_BE_SHIPPED);
        saveBillOdo(billOdo);
        return odoId;
    }

    /**
     * 生成并保存出库单明细
     * @param odoId 出库单id
     * @param orderVoList 订单vo集合
     */
    private void generateAndSaveDetailOdo(String odoId,List<OrderVo> orderVoList){
        DetailOdo detailOdo;
        for (OrderVo orderVo : orderVoList) {
            OrderDto orderDto = orderVo.getOrderDto();
            List<ListGoods> goodsVoList = orderVo.getGoodsVoList();
            String orderId = orderDto.getBillId();
            Integer statusId = orderDto.getStatusId();
            if (!statusId.equals(StatusConst.TO_BE_OUTBOUND)){
                throw new BadRequestException("出库失败，无法将待出库状态订单以外的订单出库").setErrorData(orderDto);
            }
            for (ListGoods listGoods : goodsVoList) {
                detailOdo = new DetailOdo();
                detailOdo.setBillId(odoId);
                detailOdo.setOrderId(orderId);
                Integer goodsId = listGoods.getGoodsId();
                Integer amount = listGoods.getAmount();
                detailOdo.setGoodsId(goodsId);
                detailOdo.setAmount(amount);
                detailOdoService.saveDetailOdo(detailOdo);
            }
            BillOrder billOrder = orderService.findBillById(orderId);
            billOrder.setStatusId(StatusConst.TO_BE_SHIPPED);
            orderService.saveBillOrder(billOrder);
            orderService.setBillStatus(orderId,StatusConst.TO_BE_SHIPPED);
        }
    }

    /**
     * 查询所有出库单预览信息
     * @return 所有出库单预览信息
     */
    @Deprecated
    private List<CommonBillPreviewWithUpdateTime> findAllOdoPreview(){
        return billOdoRepository.findAllOdoPreview();
    }

    /**
     * 根据出库单id查询出库单表头
     * @param odoId 出库单id
     * @return 出库单表头
     */
    @Deprecated
    private OdoDto findOdoDtoByOdoId(String odoId){
        return billOdoRepository.findOdoDtoByBillId(odoId);
    }
}
