package top.kindless.billtest.model.dto;

import lombok.NoArgsConstructor;
import top.kindless.billtest.model.common.AdminBillTitle;

import java.util.Date;

@NoArgsConstructor
public class EnterDto extends AdminBillTitle {

    public EnterDto(String billId,
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
