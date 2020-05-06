package top.kindless.billtest.model.params;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class LoginParams {

    @NotBlank(message = "用户名或密码不能为空")
    @Length(max = 16,message = "用户名长度不能大于{16}")
    private String account;

    @NotBlank(message = "用户名或密码不能为空")
    @Length(max = 16,message = "密码长度不能大于{16}")
    private String password;
}
