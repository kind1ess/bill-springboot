package top.kindless.billtest.model.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class CommonBillPreviewWithUpdateTime extends CommonBillPreview{

    @ApiModelProperty("更新时间")
    private Date updateTime;

    public CommonBillPreviewWithUpdateTime(String billId,
                                           Date createTime,
                                           Date updateTime,
                                           Integer statusId,
                                           String statusName){
        super(billId,createTime,statusId,statusName);
        this.updateTime = updateTime;
    }
}
