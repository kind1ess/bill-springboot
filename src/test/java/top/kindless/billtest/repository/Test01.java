package top.kindless.billtest.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import top.kindless.billtest.model.common.CommonBillPreviewWithUpdateTime;
import top.kindless.billtest.model.common.FlowReserveAmount;
import top.kindless.billtest.model.dto.OrderPreviewDto;

import java.util.List;

@SpringBootTest
public class Test01 {

    @Autowired
    BillOrderRepository billOrderRepository;

    @Autowired
    BillReturnRepository billReturnRepository;

    @Autowired
    DetailOdoRepository detailOdoRepository;

    @Autowired
    BillCheckRepository billCheckRepository;

    @Test
    void test(){
//        List<OrderDto> orderDtoList = billOrderRepository.findAllOrderDtoByUserId("user_f36be60651c34f318f8f49c8cb95fe96");
//        for (OrderDto orderDto : orderDtoList) {
//            System.out.println(orderDto);
//        }
//        OrderDto orderDto = billOrderRepository.findOrderDtoByBillId("order_097a7541ed1c448c96016d0fd95d117c");
//        System.out.println(orderDto);
        List<OrderPreviewDto> orderPreviewDtoList = billOrderRepository.findAllOrderPreviewDtoByUserId("user_f36be60651c34f318f8f49c8cb95fe96");
        for (OrderPreviewDto orderPreviewDto : orderPreviewDtoList) {
            System.out.println(orderPreviewDto);
        }
        System.out.println("++++++++");
        List<OrderPreviewDto> allOrderPreviewDto = billOrderRepository.findAllOrderPreviewDto();
        for (OrderPreviewDto orderPreviewDto : allOrderPreviewDto) {
            System.out.println(orderPreviewDto);
        }
    }

    @Test
    void test01(){
        List<String> allId = billReturnRepository.findAllId();
        for (String s : allId) {
            System.out.println(s);
        }
    }

    @Test
    void test02(){
        List<CommonBillPreviewWithUpdateTime> allCheckPreview = billCheckRepository.findAllCheckPreview(9,PageRequest.of(0, 2));
        for (CommonBillPreviewWithUpdateTime commonBillPreviewWithUpdateTime : allCheckPreview) {
            System.out.println(commonBillPreviewWithUpdateTime);
        }
    }

    @Test
    void test03(){
        List<FlowReserveAmount> allSumAmount = detailOdoRepository.findAllSumAmount();
        for (FlowReserveAmount flowReserveAmount : allSumAmount) {
            System.out.println(flowReserveAmount);
        }
    }
}
