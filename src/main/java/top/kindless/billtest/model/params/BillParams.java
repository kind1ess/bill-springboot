package top.kindless.billtest.model.params;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class BillParams {

    @ApiModelProperty("单据编号集合")
    private List<String> billIdList;
}
