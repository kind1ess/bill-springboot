package top.kindless.billtest.model.dto;


import lombok.NoArgsConstructor;
import top.kindless.billtest.model.common.CommonBillPreview;

import java.util.Date;


@NoArgsConstructor
public class OrderPreviewDto extends CommonBillPreview {
//    @ApiModelProperty("订单编号")
//    private String orderId;
//
//    @ApiModelProperty("创建时间")
//    private Date createTime;
//
//    @ApiModelProperty("状态id")
//    private Integer statusId;
//
//    @ApiModelProperty("状态名称")
//    private String statusName;
    public void setOrderId(String orderId){
        super.setBillId(orderId);
    }

    public OrderPreviewDto(String orderId,Date createTime,Integer statusId,String statusName){
        super(orderId,createTime,statusId,statusName);
    }
}
