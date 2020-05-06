package top.kindless.billtest.security.token;

import lombok.Data;

@Data
public class AuthToken {

    private String token;

    private Long expiredIn;

    private String userId;
}
