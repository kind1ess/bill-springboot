package top.kindless.billtest.security.context;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import top.kindless.billtest.security.auth.Authentication;

@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class AuthContextImpl implements AuthContext {

    private Authentication authentication;

    @Override
    public Authentication getAuthentication() {
        return authentication;
    }

    @Override
    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }
}
