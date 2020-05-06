package top.kindless.billtest.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "kindless")
@Component
@Data
public class KindlessProperties {

    private boolean enableSwagger = false;//是否开启swagger，默认为false

    private Long tokenExpiredIn = 3600L;//token令牌的过期时间，默认3600秒
}
