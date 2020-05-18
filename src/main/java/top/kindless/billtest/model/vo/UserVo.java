package top.kindless.billtest.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVo {

    private String userId;

    private String account;

    private String password;

    private String telephone;

    private String address;
}
