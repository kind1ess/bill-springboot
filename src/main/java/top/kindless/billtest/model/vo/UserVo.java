package top.kindless.billtest.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVo {

    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("用户账号")
    private String account;

    @ApiModelProperty("用户密码")
    private String password;

    @ApiModelProperty("用户电话")
    private String telephone;

    @ApiModelProperty("用户地址")
    private String address;
}
