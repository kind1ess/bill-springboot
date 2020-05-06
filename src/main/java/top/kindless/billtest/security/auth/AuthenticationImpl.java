package top.kindless.billtest.security.auth;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import top.kindless.billtest.model.entity.Staff;
import top.kindless.billtest.model.entity.User;

public class AuthenticationImpl implements Authentication {

    private final User user;

    private final Staff staff;

    private final String token;

    public AuthenticationImpl(@Nullable User user, @Nullable Staff staff, @NonNull String token) {
        this.user = user;
        this.staff = staff;
        this.token = token;
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public Staff getStaff() {
        return staff;
    }

    @Override
    public String getToken() {
        return token;
    }
}
