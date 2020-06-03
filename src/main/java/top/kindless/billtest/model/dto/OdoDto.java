package top.kindless.billtest.model.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import top.kindless.billtest.model.common.AdminBillTitle;

import java.util.Date;


/**
 * 出库单表头
 */
@NoArgsConstructor
public class OdoDto extends AdminBillTitle {

    public OdoDto(String billId,
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
