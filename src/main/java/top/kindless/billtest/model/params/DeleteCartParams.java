package top.kindless.billtest.model.params;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class DeleteCartParams {

    @ApiModelProperty(name = "购物车id集合")
    private List<Integer> cartIdList;
}
