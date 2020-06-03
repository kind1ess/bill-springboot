package top.kindless.billtest.model.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminBillTitle extends BillTitle{

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("管理员id")
    private String staffId;

    @ApiModelProperty("管理员姓名")
    private String staffName;

    @ApiModelProperty("管理员电话")
    private String staffTelephone;

    public AdminBillTitle(String billId,
                          Date createTime,
                          Integer statusId,
                          String statusName,
                          Date updateTime,
                          String staffId,
                          String staffName,
                          String staffTelephone){
        super(billId, createTime, statusId, statusName);
        this.updateTime = updateTime;
        this.staffId = staffId;
        this.staffName = staffName;
        this.staffTelephone = staffTelephone;
    }
}
