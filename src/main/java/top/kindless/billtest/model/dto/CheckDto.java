package top.kindless.billtest.model.dto;

import lombok.NoArgsConstructor;
import top.kindless.billtest.model.common.AdminBillTitle;

import java.util.Date;

@NoArgsConstructor
public class CheckDto extends AdminBillTitle {

    public CheckDto(String billId,
                    Date createTime,
                    Integer statusId,
                    String statusName,
                    Date updateTime,
                    String staffId,
                    String staffName,
                    String staffTelephone){
        super(billId, createTime, statusId, statusName, updateTime, staffId, staffName, staffTelephone);
    }
}
